package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.FamilyDao;
import com.cdutcm.healthy.dao.SugarDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.entity.Family;
import com.cdutcm.healthy.dataobject.entity.Pressure;
import com.cdutcm.healthy.dataobject.entity.Sugar;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.SugarForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.BodyIndexVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarTendencyVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarVO;
import com.cdutcm.healthy.enums.*;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.*;
import com.cdutcm.healthy.utils.CalendarUtil;
import com.cdutcm.healthy.utils.EnumUtil;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import com.cdutcm.healthy.utils.converter.Sugar2SugarTendencyVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 19:01 星期日
 * @Description :
 */
@Slf4j
@Service
public class SugarServiceImpl implements SugarService {
    @Autowired
    private SugarDao sugarDao;
    @Autowired
    private FamilyDao familyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SmsService smsService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatMpService weChatMpService;
    @Autowired
    private RedisOperator redisOperator;

    @Override
    public boolean recordSugar(SugarForm sugarForm, String openid) {
        Sugar sugar = new Sugar();
        sugar.setSugarId(KeyUtil.getUniqueKeyTime());
        sugar.setOpenid(openid);
        sugar.setSugarDatetime(sugarForm.getSugarDatetime());
        sugar.setSugarValue(sugarForm.getSugarValue());
        Integer sugarType = getSugarType(sugarForm.getSugarValue());
        sugar.setSugarType(sugarType);
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        sugar.setLocation(location);// 用户测量地理位置
        try {
            sugarDao.insertSugar(sugar);
        } catch (Exception e) {
            log.error("【记录血糖】血压记录失败，sugar = {}，openid = {}", sugar, openid);
            throw new HealthyException(ResultEnum.SUGAR_SAVE_ERROR);
        }
        // 发送短信通知
        sendSms(openid, sugarType, location);
        return true;
    }

    @Override
    public boolean recordSugar(Long datetime, Double sugarValue, String openid) {
        Sugar sugar = new Sugar();
        sugar.setSugarId(KeyUtil.getUniqueKeyTime());
        sugar.setOpenid(openid);
        sugar.setSugarDatetime(new Date(datetime));
        sugar.setSugarValue(sugarValue);
        Integer sugarType = getSugarType(sugarValue);
        sugar.setSugarType(sugarType);
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        sugar.setLocation(location);// 用户测量地理位置
        // 发送短信通知
        sendSms(openid, sugarType, location);
        return sugarDao.insertSugar(sugar);
    }

    //根据血糖指数获取当前记录的血糖类型
    private Integer getSugarType(Double sugarValue) {
        Double[] standards = {7.0, 6.0, 3.7, 0.0};//世界卫生组织权威定义测量结果类型-血糖
        SugarTypeEnum sugarTypes[] = {SugarTypeEnum.SERIOUS, SugarTypeEnum.DAMAGE, SugarTypeEnum.NORMAL, SugarTypeEnum.LOW};//世界卫生组织权威定义测量-类型
        for (int i = 0; i < sugarTypes.length; i++) {
            if (sugarValue > standards[i]) {
                return sugarTypes[i].getCode();
            }
        }
        log.error("【记录血糖】参数错误，sugarValue = {}", sugarValue);
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    @Override
    public String measureSugar(Double sugarValue) {
        Integer sugarType = getSugarType(sugarValue);
        return EnumUtil.getByCode(sugarType, SugarTypeEnum.class).getMsg();
    }

    @Override
    public SugarVO newlySugar(String openid) {
        LambdaQueryWrapper<Sugar> queryWrapper = new LambdaQueryWrapper<Sugar>()
                .eq(Sugar::getOpenid, openid)
                .orderByDesc(Sugar::getCreateTime)
                .last("limit 1");
        Sugar sugar = sugarDao.selectOne(queryWrapper);
        return Entity2VO.convert(sugar, SugarVO.class);
    }

    @Override
    public SugarCensusVO censusSugar(String openid) {
        //定义三个月前的时间
        Calendar calendar = CalendarUtil.getIntervalNow(Calendar.MONTH, -3);
        //构建查询条件
        LambdaQueryWrapper<Sugar> queryWrapper = new LambdaQueryWrapper<Sugar>()
                .eq(Sugar::getOpenid, openid)
                .gt(Sugar::getSugarDatetime, calendar.getTime())
                .orderByAsc(Sugar::getSugarDatetime);
        List<Sugar> sugarList = sugarDao.selectList(queryWrapper);
        //统计信息
        SugarCensusVO sugarCensusVO = new SugarCensusVO();
        sugarCensusVO.setTotal(sugarList.size());
        final Double[] sugarValue = {Double.MAX_VALUE, Double.MIN_VALUE, 0.0};//最低血糖, 最高血糖，平均血糖
        Map<String, SugarTendencyVO> map = new LinkedHashMap<>();//血糖变化趋势
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        sugarList.forEach(sugar -> {
            //近三个月统计
            if (sugar.getSugarType().equals(SugarTypeEnum.NORMAL.getCode())) {//正常血糖次数
                sugarCensusVO.addNormal();
            } else if (sugar.getSugarType() > SugarTypeEnum.NORMAL.getCode()) {//偏高血糖次数
                sugarCensusVO.addHigh();
            }
            if (sugar.getSugarValue() < sugarValue[0]) {//最低血糖
                sugarValue[0] = sugar.getSugarValue();
            }
            if (sugar.getSugarValue() > sugarValue[1]) {//最高血糖
                sugarValue[1] = sugar.getSugarValue();
            }
            sugarValue[2] += sugar.getSugarValue() / sugarCensusVO.getTotal();//平均血糖
            //变化趋势

            String datetime = sdf.format(sugar.getSugarDatetime());
            if (map.containsKey(datetime)) {//已经存在日期，则取平均值
                SugarTendencyVO sugarTendencyVO = map.get(datetime);
                sugarTendencyVO.add(Sugar2SugarTendencyVO.convert(sugar));
                map.put(datetime, sugarTendencyVO);
            } else {//不存在当前日期，则直接放入map
                map.put(datetime, Sugar2SugarTendencyVO.convert(sugar));
            }
        });
        sugarCensusVO.setLowValue(sugarValue[0]);//最低血糖
        sugarCensusVO.setHighValue(sugarValue[1]);//最高血糖
        sugarCensusVO.setAverage(sugarValue[2]);//平均血糖
        sugarCensusVO.setTendency(map);//血糖变化趋势
        return sugarCensusVO;
    }

    @Override
    public IPageVO historySugar(String openid, PageForm page) {
        //构建查询条件：用户为当前用户
        LambdaQueryWrapper<Sugar> queryWrapper = new LambdaQueryWrapper<Sugar>()
                .eq(Sugar::getOpenid, openid)
                .orderByDesc(Sugar::getSugarDatetime);
        IPage sugarIPage = sugarDao.selectPage(PageForm2Page.convert(page), queryWrapper);//根据分页，获取所有当前用户的血糖记录IPage
        //数据处理
        //数据去重
        List<Sugar> sugarList = sugarIPage.getRecords();//获取所有当前用户的血糖记录List
        Map<String, List<SugarTendencyVO>> map = new LinkedHashMap<>();//合并日期date相同的数据，将其归入List<SugarTendencyVO>
        SimpleDateFormat dateSDF = new SimpleDateFormat("MM月dd日");//日期转换格式
        List<SugarTendencyVO> sugarVOList;//合并相同日期记录的List<SugarTendencyVO>
        for (Sugar sugar : sugarList) {
            String date = dateSDF.format(sugar.getSugarDatetime());//转换日期
            if (map.containsKey(date)) {//如果map中已经存在日期date，就从map获取List<SugarTendencyVO>，然后将当前记录的数据归入List<SugarTendencyVO>
                sugarVOList = map.get(date);
            } else {
                sugarVOList = new ArrayList<>();//如果map中没有日期date，就重新初始化List<SugarTendencyVO>
            }
            sugarVOList.add(Sugar2SugarTendencyVO.convert(sugar));//将Sugar转化为SugarTendencyVO，并将转化后的SugarTendencyVO归入到List<SugarTendencyVO>
            map.put(date, sugarVOList);//然后在添加到map中
        }
        //数据格式转化Map转List
        List<Map> list = new ArrayList<>();
        list.add(map);
        IPageVO iPageVO = IPage2IPageVO.convert(sugarIPage);
        iPageVO.setRecords(list);
        return iPageVO;
    }

    /**
     * 发送高血糖状况短信
     *
     * @param uOpenid
     * @param sugarType
     */
    private void sendSms(String uOpenid, Integer sugarType, String location) {
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(uOpenid);

            // 获取亲属电话号码列表
            List<Family> familyList = familyDao.select(uOpenid);
            List<String> phoneList = familyList.stream()
                    .map(Family::getPhone).collect(Collectors.toList());
            // 获取亲属电话号码列表
            List<String> openidList = familyList.stream()
                    .map(Family::getFOpenid).collect(Collectors.toList());

            // 发送短信通知家属高血糖重度状况
            if (sugarType.equals(SugarTypeEnum.SERIOUS.getCode())) {
                smsService.sendHealthySms(
                        phoneList,// 发送亲属电话号码列表
                        wxMpUser.getNickname(),// 用户微信昵称
                        TweetsTypeEnum.SUGAR.getMsg().concat(EnumUtil.getByCode(sugarType, SugarTypeEnum.class).getMsg())
                ); //发送消息
            }
            // 发送微信模板消息通知家属高血糖受损状况
            BodyIndexVO bodyIndexVO = new BodyIndexVO();
            bodyIndexVO.setOpenid(openidList);
            bodyIndexVO.setNickname(wxMpUser.getNickname());
            bodyIndexVO.setLocation(location);
            bodyIndexVO.setType(TweetsTypeEnum.SUGAR.getMsg());
            bodyIndexVO.setLevel(EnumUtil.getByCode(sugarType, SugarTypeEnum.class).getMsg());
            bodyIndexVO.setPhone(userDao.selectPhoneByOpenid(uOpenid));
            weChatMpService.sendWxMpTempMsg(bodyIndexVO);
        } catch (Exception e) {
            log.error("【短信通知】高血糖短信通知失败，e = {}", e);
        }
    }
}
