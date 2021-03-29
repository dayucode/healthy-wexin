package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.FamilyDao;
import com.cdutcm.healthy.dao.PressureDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.entity.Family;
import com.cdutcm.healthy.dataobject.entity.Pressure;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.PressureForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.BodyIndexVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureTendencyVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureVO;
import com.cdutcm.healthy.enums.PressureTypeEnum;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.TweetsTypeEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.PressureService;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.SmsService;
import com.cdutcm.healthy.service.WeChatMpService;
import com.cdutcm.healthy.utils.CalendarUtil;
import com.cdutcm.healthy.utils.EnumUtil;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import com.cdutcm.healthy.utils.converter.Pressure2PressureTendencyVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 21:18 星期四
 * @Description :
 */
@Slf4j
@Service
public class PressureServiceImpl implements PressureService {
    @Autowired
    private PressureDao pressureDao;
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
    public boolean recordPressure(PressureForm pressureForm, String openid) {
        // 比较舒张压必须必收缩压低
        if (pressureForm.getLowPressure() > pressureForm.getHighPressure()) {
            log.error("【记录血压】舒张压高于收缩压，pressureForm = {}", pressureForm);
            throw new HealthyException(ResultEnum.PRESSURE_PARAM_ERROR);
        }

        Pressure pressure = new Pressure();
        pressure.setPressureDatetime(pressureForm.getPressureDatetime());
        BeanUtils.copyProperties(pressureForm, pressure);
        pressure.setPressureId(KeyUtil.getUniqueKeyTime());
        pressure.setOpenid(openid);
        Integer pressureType = getPressureType(pressureForm.getHighPressure(), pressureForm.getLowPressure());
        pressure.setPressureType(pressureType);// 血压类型
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        pressure.setLocation(location);// 用户测量地理位置
        try {
            pressureDao.insertPressure(pressure);
        } catch (Exception e) {
            log.error("【记录血压】血压记录失败，pressure = {}，openid = {}", pressure, openid);
            throw new HealthyException(ResultEnum.PRESSURE_SAVE_ERROR);
        }

        // 发送短信通知
        sendSms(openid, pressureType, location);
        return true;
    }

    @Override
    public boolean recordPressure(Long datetime, Double highPressure, Double lowPressure, String openid) {
        // 构建血压测量
        Pressure pressure = new Pressure();
        pressure.setPressureDatetime(new Date(datetime));
        pressure.setHighPressure(highPressure);
        pressure.setLowPressure(lowPressure);
        pressure.setPressureId(KeyUtil.getUniqueKeyTime());
        pressure.setOpenid(openid);
        Integer pressureType = getPressureType(highPressure, lowPressure);
        pressure.setPressureType(pressureType);
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        pressure.setLocation(location);// 用户测量地理位置
        //发送短信通知
        sendSms(openid, pressureType, location);
        return pressureDao.insertPressure(pressure);
    }

    //根据收缩压和舒张压获取当前记录的血压类型
    private Integer getPressureType(Double highPressure, Double lowPressure) {
        Double[] highStandards = {180.0, 160.0, 140.0, 120.0, 90.0, 0.0};//世界卫生组织权威定义测量-收缩压
        Double[] lowStandards = {110.0, 100.0, 90.0, 80.0, 60.0, 0.0};//世界卫生组织权威定义测量-舒张压
        PressureTypeEnum pressureTypes[] = {PressureTypeEnum.SERIOUS, PressureTypeEnum.MODERATE, PressureTypeEnum.LIGHT, PressureTypeEnum.HIGH, PressureTypeEnum.NORMAL, PressureTypeEnum.LOW};//世界卫生组织权威定义测量-类型
        Integer highPressureType = getPressureType(highPressure, pressureTypes, highStandards);//获得收缩压的血压测量类型
        Integer lowPressureType = getPressureType(lowPressure, pressureTypes, lowStandards);//获得舒张压的血压测量类型
        // 返回等级较高的血压类型
        return Math.abs(highPressureType) > Math.abs(lowPressureType) ? highPressureType : lowPressureType;
    }

    //根据血压获取本次测量的血压类型
    private Integer getPressureType(Double pressure, PressureTypeEnum[] pressureTypes, Double[] standards) {
        for (int i = 0; i < pressureTypes.length; i++) {
            if (pressure >= standards[i]) {
                return pressureTypes[i].getCode();
            }
        }
        log.error("【记录血压】参数错误，PRESSURE = {}", pressure);
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    @Override
    public String measurePressure(Double highPressure, Double lowPressure) {
        // 比较舒张压必须必收缩压低
        if (lowPressure > highPressure) {
            log.error("【记录血压】舒张压高于收缩压，lowPressure = {}，highPressure = {}", lowPressure, highPressure);
            throw new HealthyException(ResultEnum.PRESSURE_PARAM_ERROR);
        }
        Integer pressureType = getPressureType(highPressure, lowPressure);
        return EnumUtil.getByCode(pressureType, PressureTypeEnum.class).getMsg();
    }

    @Override
    public PressureVO newlyPressure(String openid) {
        LambdaQueryWrapper<Pressure> queryWrapper = new LambdaQueryWrapper<Pressure>()
                .eq(Pressure::getOpenid, openid)
                .orderByDesc(Pressure::getCreateTime)
                .last("limit 1");
        Pressure pressure = pressureDao.selectOne(queryWrapper);
        return Entity2VO.convert(pressure, PressureVO.class);
    }

    @Override
    public PressureCensusVO censusPressure(String openid) {
        //定义三个月前的时间
        Calendar calendar = CalendarUtil.getIntervalNow(Calendar.MONTH, -3);
        //构建查询条件
        LambdaQueryWrapper<Pressure> queryWrapper = new LambdaQueryWrapper<Pressure>()
                .eq(Pressure::getOpenid, openid)
                .gt(Pressure::getPressureDatetime, calendar.getTime())
                .orderByAsc(Pressure::getPressureDatetime);
        List<Pressure> pressureList = pressureDao.selectList(queryWrapper);
        //统计信息
        PressureCensusVO pressureCensusVO = new PressureCensusVO();
        pressureCensusVO.setTotal(pressureList.size());//总记录数
        final Double[] pressureValue = {Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};//舒张压最高值, 收缩压最高值, 最大静脉差
        Map<String, PressureTendencyVO> map = new LinkedHashMap<>();//血压变化趋势
        pressureList.forEach(pressure -> {
            //近三个月统计
            if (pressure.getPressureType().equals(PressureTypeEnum.NORMAL.getCode())) {//正常血压次数
                pressureCensusVO.addNormal();
            } else if (pressure.getPressureType() > PressureTypeEnum.NORMAL.getCode()) {//高血压次数
                pressureCensusVO.addHigh();
            }
            if (pressure.getLowPressure() > pressureValue[0]) {//获取舒张压最高值
                pressureValue[0] = pressure.getLowPressure();
            }
            if (pressure.getHighPressure() > pressureValue[1]) {//获取收缩压最高值
                pressureValue[1] = pressure.getHighPressure();
            }
            double venous = pressure.getVenous();
            if (venous > pressureValue[2]) {//获取最大静脉差
                pressureValue[2] = venous;
            }
            //变化趋势
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
            String datetime = sdf.format(pressure.getPressureDatetime());
            if (map.containsKey(datetime)) {//已经存在日期，则取平均值
                PressureTendencyVO pressureTendencyVO = map.get(datetime);
                pressureTendencyVO.add(Pressure2PressureTendencyVO.convert(pressure));
                map.put(datetime, pressureTendencyVO);
            } else {//不存在当前日期，则直接放入map
                map.put(datetime, Pressure2PressureTendencyVO.convert(pressure));
            }
        });
        pressureCensusVO.setLowValue(pressureValue[0]);//舒张压最高值
        pressureCensusVO.setHighValue(pressureValue[1]);//收缩压最高值
        pressureCensusVO.setVenous(pressureValue[2]);//最大静脉差
        pressureCensusVO.setTendency(map);//血压变化趋势
        return pressureCensusVO;
    }

    @Override
    public IPageVO historyPressure(String openid, PageForm page) {
        //构建查询条件：用户为当前用户
        LambdaQueryWrapper<Pressure> queryWrapper = new LambdaQueryWrapper<Pressure>()
                .eq(Pressure::getOpenid, openid)
                .orderByDesc(Pressure::getPressureDatetime);
        //根据分页，获取所有当前用户的血压记录IPage
        IPage pressureIPage = pressureDao.selectPage(PageForm2Page.convert(page), queryWrapper);
        //数据去重处理
        //获取所有当前用户的血压记录List
        List<Pressure> pressureList = pressureIPage.getRecords();
        //合并日期date相同的数据，将其归入List<PressureTendencyVO>
        Map<String, List<PressureTendencyVO>> map = new LinkedHashMap<>();
        SimpleDateFormat dateSDF = new SimpleDateFormat("MM月dd日");//日期转换格式
        //合并相同日期记录的List<PressureTendencyVO>
        List<PressureTendencyVO> pressureVOList;
        for (Pressure pressure : pressureList) {
            String date = dateSDF.format(pressure.getPressureDatetime());//转换日期
            //如果map中已经存在日期date，就从map获取List<PressureTendencyVO>，然后将当前记录的数据归入List<PressureTendencyVO>
            if (map.containsKey(date)) {
                pressureVOList = map.get(date);
            } else {
                //如果map中没有日期date，就重新初始化List<PressureTendencyVO>
                pressureVOList = new ArrayList<>();
            }
            //将Pressure转化为PressureTendencyVO，并将转化后的PressureTendencyVO归入到List<PressureTendencyVO>
            pressureVOList.add(Pressure2PressureTendencyVO.convert(pressure));
            map.put(date, pressureVOList);//然后在添加到map中
        }
        //数据格式转化Map转List
        List<Map> list = new ArrayList<>();
        list.add(map);
        IPageVO iPageVO = IPage2IPageVO.convert(pressureIPage);
        iPageVO.setRecords(list);
        return iPageVO;
    }

    /**
     * 发送高血压状况短信
     *
     * @param uOpenid
     * @param pressureType
     */
    private void sendSms(String uOpenid, Integer pressureType, String location) {
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(uOpenid);
            // 获取亲属电话号码列表
            List<Family> familyList = familyDao.select(uOpenid);
            List<String> phoneList = familyList.stream()
                    .map(Family::getPhone).collect(Collectors.toList());
            // 获取亲属电话号码列表
            List<String> openidList = familyList.stream()
                    .map(Family::getFOpenid).collect(Collectors.toList());
            // 发送短信通知家属高血压重度状况
            if (pressureType.equals(PressureTypeEnum.SERIOUS.getCode())) {
                smsService.sendHealthySms(
                        phoneList,// 发送亲属电话号码列表
                        wxMpUser.getNickname(), // 用户微信昵称
                        TweetsTypeEnum.PRESSURE.getMsg().concat(EnumUtil.getByCode(pressureType, PressureTypeEnum.class).getMsg())); //发送消息
            }
            // 发送微信模板消息通知家属高血压中度状况
            BodyIndexVO bodyIndexVO = new BodyIndexVO();
            bodyIndexVO.setOpenid(openidList);
            bodyIndexVO.setNickname(wxMpUser.getNickname());
            bodyIndexVO.setLocation(location);
            bodyIndexVO.setType(TweetsTypeEnum.PRESSURE.getMsg());
            bodyIndexVO.setLevel(EnumUtil.getByCode(pressureType, PressureTypeEnum.class).getMsg());
            bodyIndexVO.setPhone(userDao.selectPhoneByOpenid(uOpenid));
            weChatMpService.sendWxMpTempMsg(bodyIndexVO);
        } catch (Exception e) {
            log.error("【短信通知】高血压短信通知失败，e = {}", e);
        }
    }
}
