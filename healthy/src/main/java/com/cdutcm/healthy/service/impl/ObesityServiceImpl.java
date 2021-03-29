package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.FamilyDao;
import com.cdutcm.healthy.dao.ObesityDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.entity.Family;
import com.cdutcm.healthy.dataobject.entity.Obesity;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.dataobject.form.user.ObesityForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.BodyIndexVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityTendencyVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityVO;
import com.cdutcm.healthy.enums.*;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.ObesityService;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.SmsService;
import com.cdutcm.healthy.service.WeChatMpService;
import com.cdutcm.healthy.utils.CalendarUtil;
import com.cdutcm.healthy.utils.EnumUtil;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.Obesity2ObesityTendencyVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 19:01 星期日
 * @Description :
 */
@Slf4j
@Service
public class ObesityServiceImpl implements ObesityService {
    @Autowired
    private ObesityDao obesityDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FamilyDao familyDao;
    @Autowired
    private SmsService smsService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatMpService weChatMpService;
    @Autowired
    private RedisOperator redisOperator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recordObesity(ObesityForm obesityForm, String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            log.error("【记录体重】该用户不存在，openid = {}", openid);
            throw new HealthyException(ResultEnum.USER_NOT_EXIST);
        }
        // 体质指数（BMI）=体重（kg）÷身高^2（m）
        Double bmi = obesityForm.getObesityValue() / Math.pow(user.getHeight() / 100, 2);
        Obesity obesity = new Obesity();
        obesity.setObesityId(KeyUtil.getUniqueKeyTime());
        obesity.setOpenid(openid);
        obesity.setObesityDatetime(obesityForm.getObesityDatetime());
        obesity.setObesityValue(obesityForm.getObesityValue());
        obesity.setBmi(bmi);
        Integer obesityType = getObesityType(bmi);
        obesity.setObesityType(obesityType);
        user.setWeight(obesity.getObesityValue());
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        // 用户测量地理位置
        obesity.setLocation(location);
        try {
            obesityDao.insertObesity(obesity);
            userDao.updateUser(user);
        } catch (Exception e) {
            log.error("【记录体重】血压记录失败，obesity = {}，openid = {}", obesity, openid);
            throw new HealthyException(ResultEnum.PRESSURE_SAVE_ERROR);
        }

        // 发送短信通知
        sendSms(openid, obesityType, location);
        return true;
    }

    @Override
    public boolean recordObesity(Long datetime, Double obesityValue, String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            log.error("【记录体重】该用户不存在，openid = {}", openid);
            throw new HealthyException(ResultEnum.USER_NOT_EXIST);
        }
        // 体质指数（BMI）=体重（kg）÷身高^2（m）
        Double bmi = obesityValue / Math.pow(user.getHeight() / 100, 2);
        // 拼装体重测量信息
        Obesity obesity = new Obesity();
        obesity.setObesityId(KeyUtil.getUniqueKeyTime());
        obesity.setOpenid(openid);
        obesity.setObesityDatetime(new Date(datetime));
        obesity.setObesityValue(obesityValue);
        obesity.setBmi(bmi);
        Integer obesityType = getObesityType(bmi);
        obesity.setObesityType(obesityType);
        user.setWeight(obesity.getObesityValue());
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        // 用户测量地理位置
        obesity.setLocation(location);

        // 插入体重测量信息
        obesityDao.insertObesity(obesity);
        // 修改用户信息的身高信息
        userDao.updateUser(user);
        // 发送短信通知
        sendSms(openid, obesityType, location);
        return true;
    }

    /**
     * 根据体重指数获取当前记录的体重类型
     * @param bmi BMI指数值
     * @return BMI体重类型
     */
    private Integer getObesityType(Double bmi) {
        // 世界卫生组织权威定义测量结果类型-体重
        Double[] standards = {28.0, 24.0, 18.4, 0.0};
        // 世界卫生组织权威定义测量-类型
        ObesityTypeEnum[] obesityTypeEnums = {ObesityTypeEnum.OBESITY, ObesityTypeEnum.OVERWEIGHT, ObesityTypeEnum.HEALTHY, ObesityTypeEnum.LIGHTER};
        for (int i = 0; i < obesityTypeEnums.length; i++) {
            if (bmi >= standards[i]) {
                return obesityTypeEnums[i].getCode();
            }
        }
        log.error("【记录体重】参数错误，bmi = {}", bmi);
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    @Override
    public String measureObesity(Double obesityValue, String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            log.error("【测量体重】该用户不存在，openid = {}", openid);
            throw new HealthyException(ResultEnum.USER_NOT_EXIST);
        }
        // 体质指数（BMI）=体重（kg）÷身高^2（m）
        Double bmi = obesityValue / Math.pow(user.getHeight() / 100, 2);
        return EnumUtil.getByCode(getObesityType(bmi), ObesityTypeEnum.class).getMsg();
    }

    @Override
    public ObesityVO newlyObesity(String openid) {
        LambdaQueryWrapper<Obesity> queryWrapper = new LambdaQueryWrapper<Obesity>()
                .eq(Obesity::getOpenid, openid)
                .orderByDesc(Obesity::getCreateTime)
                .last("limit 1");
        Obesity obesity = obesityDao.selectOne(queryWrapper);
        return Entity2VO.convert(obesity, ObesityVO.class);
    }

    @Override
    public ObesityCensusVO censusObesity(String openid) {
        //定义三个月前的时间
        Calendar calendar = CalendarUtil.getIntervalNow(Calendar.MONTH, -3);
        //构建查询条件
        LambdaQueryWrapper<Obesity> queryWrapper = new LambdaQueryWrapper<Obesity>()
                .eq(Obesity::getOpenid, openid)
                .gt(Obesity::getObesityDatetime, calendar.getTime())
                .orderByAsc(Obesity::getObesityDatetime);
        List<Obesity> obesityList = obesityDao.selectList(queryWrapper);
        //统计信息
        ObesityCensusVO obesityCensusVO = new ObesityCensusVO();
        obesityCensusVO.setTotal(obesityList.size());
        // 体重最高值，体重最低值
        final Double[] obesityValue = {Double.MIN_VALUE, Double.MAX_VALUE};
        // 体重变化趋势
        Map<String, ObesityTendencyVO> map = new LinkedHashMap<>();
        obesityList.forEach(obesity -> {
            // 近三个月统计
            // 体重最高值
            if (obesity.getObesityValue() > obesityValue[0]) {
                obesityValue[0] = obesity.getObesityValue();
            }
            // 体重最低值
            if (obesity.getObesityValue() < obesityValue[1]) {
                obesityValue[1] = obesity.getObesityValue();
            }
            // 变化趋势
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
            String datetime = sdf.format(obesity.getObesityDatetime());
            // 已经存在日期，则取平均值
            if (map.containsKey(datetime)) {
                ObesityTendencyVO obesityTendencyVO = map.get(datetime);
                obesityTendencyVO.add(Obesity2ObesityTendencyVO.convert(obesity));
                map.put(datetime, obesityTendencyVO);
            } else {// 不存在当前日期，则直接放入map
                map.put(datetime, Obesity2ObesityTendencyVO.convert(obesity));
            }
        });
        if (obesityList.size() == 0) {
            obesityValue[0] = 0.0;
            obesityValue[0] = 0.0;
        }
        obesityCensusVO.setLowValue(obesityValue[0]);// 体重最高值
        obesityCensusVO.setHighValue(obesityValue[1]);// 体重最低值
        obesityCensusVO.setTendency(map);// 体重变化趋势
        return obesityCensusVO;
    }

    @Override
    public IPageVO historyObesity(String openid, PageForm page) {
        //构建查询条件：用户为当前用户
        LambdaQueryWrapper<Obesity> queryWrapper = new LambdaQueryWrapper<Obesity>()
                .eq(Obesity::getOpenid, openid)
                .orderByDesc(Obesity::getObesityDatetime);
        // 根据分页，获取所有当前用户的体重记录IPage
        IPage obesityIPage = obesityDao.selectPage(PageForm2Page.convert(page), queryWrapper);

        //数据处理
        //数据去重
        // 获取所有当前用户的体重记录List
        List<Obesity> obesityList = obesityIPage.getRecords();
        // 合并日期date相同的数据，将其归入List<ObesityTendencyVO>
        Map<String, List<ObesityTendencyVO>> map = new LinkedHashMap<>();
        // 日期转换格式
        SimpleDateFormat dateSDF = new SimpleDateFormat("MM月dd日");
        List<ObesityTendencyVO> obesityTendencyVOList;//合并相同日期记录的List<ObesityTendencyVO>
        for (Obesity obesity : obesityList) {
            // 转换日期
            String date = dateSDF.format(obesity.getObesityDatetime());
            // 如果map中已经存在日期date，就从map获取List<ObesityTendencyVO>，然后将当前记录的数据归入List<ObesityTendencyVO>
            if (map.containsKey(date)) {
                obesityTendencyVOList = map.get(date);
            } else {
                obesityTendencyVOList = new ArrayList<>();// 如果map中没有日期date，就重新初始化List<ObesityTendencyVO>
            }
            obesityTendencyVOList.add(Obesity2ObesityTendencyVO.convert(obesity));// 将Obesity转化为ObesityTendencyVO，并将转化后的ObesityTendencyVO归入到List<ObesityTendencyVO>
            map.put(date, obesityTendencyVOList);// 然后在添加到map中
        }
        //数据格式转化Map转List
        List<Map> list = new ArrayList<>();
        list.add(map);
        IPageVO iPageVO = IPage2IPageVO.convert(obesityIPage);
        iPageVO.setRecords(list);
        return iPageVO;
    }

    /**
     * 发送体重状况短信
     *
     * @param uOpenid
     * @param obesityType
     */
    private void sendSms(String uOpenid, Integer obesityType, String location) {
        try {
            // 根据openid获取微信用户信息类
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(uOpenid);
            // 获取亲属电话号码列表
            List<Family> familyList = familyDao.select(uOpenid);
            List<String> phoneList = familyList.stream()
                    .map(Family::getPhone).collect(Collectors.toList());
            // 获取亲属电话号码列表
            List<String> openidList = familyList.stream()
                    .map(Family::getFOpenid).collect(Collectors.toList());

            // 发送短信通知家属 体重肥胖 状况
            if (obesityType.equals(ObesityTypeEnum.OBESITY.getCode())) {
                smsService.sendHealthySms(
                        phoneList,// 发送亲属电话号码列表
                        wxMpUser.getNickname(), // 用户微信昵称
                        TweetsTypeEnum.OBESITY.getMsg().concat(EnumUtil.getByCode(obesityType, ObesityTypeEnum.class).getMsg())); //发送消息
            }
            // 发送微信模板消息通知家属 体重超重 状况
            BodyIndexVO bodyIndexVO = new BodyIndexVO();
            bodyIndexVO.setOpenid(openidList);
            bodyIndexVO.setNickname(wxMpUser.getNickname());
            bodyIndexVO.setLocation(location);
            bodyIndexVO.setType(TweetsTypeEnum.OBESITY.getMsg());
            bodyIndexVO.setLevel(EnumUtil.getByCode(obesityType, ObesityTypeEnum.class).getMsg());
            bodyIndexVO.setPhone(userDao.selectPhoneByOpenid(uOpenid));
            weChatMpService.sendWxMpTempMsg(bodyIndexVO);
        } catch (Exception e) {
            log.error("【短信通知】体重短信通知失败，e = {}", e);
        }
    }
}
