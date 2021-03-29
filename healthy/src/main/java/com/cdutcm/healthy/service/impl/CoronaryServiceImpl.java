package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.CoronaryDao;
import com.cdutcm.healthy.dao.FamilyDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.entity.Coronary;
import com.cdutcm.healthy.dataobject.entity.Family;
import com.cdutcm.healthy.dataobject.form.user.CoronaryForm;
import com.cdutcm.healthy.dataobject.vo.user.BodyIndexVO;
import com.cdutcm.healthy.dataobject.vo.user.CoronaryVO;
import com.cdutcm.healthy.enums.*;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.CoronaryService;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.SmsService;
import com.cdutcm.healthy.service.WeChatMpService;
import com.cdutcm.healthy.utils.EnumUtil;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 0:01 星期二
 * @Description :
 */
@Slf4j
@Service
public class CoronaryServiceImpl implements CoronaryService {
    @Autowired
    private CoronaryDao coronaryDao;
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
    public CoronaryVO assessCoronary(CoronaryForm coronaryForm, String openid) {
        Coronary coronary = new Coronary();
        BeanUtils.copyProperties(coronaryForm, coronary);
        coronary.setCoronaryId(KeyUtil.getUniqueKeyTime());
        coronary.setOpenid(openid);
        coronary.setCoronaryValue(getCoronaryValue(coronaryForm));//设置风险值
        Integer coronaryStatus = getCoronaryStatus(coronary.getCoronaryValue());
        coronary.setCoronaryStatus(coronaryStatus);//设置风险等级
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        coronary.setLocation(location);// 用户测量地理位置
        try {
            coronaryDao.insertCoronary(coronary);
        } catch (Exception e) {
            log.error("【冠心病风险评估】保存评估数据失败，coronary = {}", coronary);
            throw new HealthyException(ResultEnum.CORONARY_SAVE_ERROR);
        }
        // 冠心病测量结果,发送短信通知
        sendSms(openid, coronaryStatus, location);
        return Entity2VO.convert(coronary, CoronaryVO.class);
    }

    //通过冠心病风险值获取冠心病风险等级
    private Integer getCoronaryStatus(Double coronaryValue) {
        Long coronaryValueRound = Math.round(coronaryValue);//风险值为小数，则四舍五入取整
        Integer[] coronaryValues = {10, 20, 30};
        CoronaryStatusEnum[] coronaryStatusEnums = {CoronaryStatusEnum.LOW, CoronaryStatusEnum.Middle, CoronaryStatusEnum.HIGH};
        for (int i = 0; i < coronaryStatusEnums.length; i++) {
            if (coronaryValueRound <= coronaryValues[i]) {
                return coronaryStatusEnums[i].getCode();
            }
        }
        return CoronaryStatusEnum.HIGH.getCode();//风险值超过30%，则均取30%
    }

    //根据CoronaryForm计算冠心病风险值
    private Double getCoronaryValue(CoronaryForm coronaryForm) {
        Calendar calendar = new GregorianCalendar();
        Double coronaryValue = 0.0;
        //1.性别
        //2.年龄
        coronaryValue += getCoronaryValueByAge(coronaryForm.getCoronaryAge());
        //3.总胆固醇（TC）（mmol/L）
        coronaryValue += getCoronaryValueByTc(coronaryForm.getTc());
        //4.是否吸烟
        coronaryValue += getCoronaryValueBySmoke(coronaryForm.getCoronarySmoke());
        //5.高密度脂蛋白（HDL-C）
        coronaryValue += getCoronaryValueByHdlC(coronaryForm.getHdlC());
        //6.收缩压（SBP）（mmHg）
        coronaryValue += getCoronaryValueBySbp(coronaryForm.getSbp());
        return coronaryValue;
    }

    /**
     * 根据年龄计算冠心病风险值
     *
     * @param coronaryAge 冠心病年龄
     * @return 冠心病风险值
     */
    private Double getCoronaryValueByAge(Integer coronaryAge) {
        Integer[] ages = {34, 39, 44, 49, 54, 59, 64, 69, 74, 79};
        Double[] coronaryValue = {0.5, 0.5, 1.0, 2.0, 4.0, 6.0, 12.0, 16.0, 20.0, 25.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (coronaryAge <= ages[i]) {
                return coronaryValue[i];
            }
        }
        return 25.0;//大于79岁，风险值均为25
    }

    private Double getCoronaryValueByAge(String coronaryAge) {
        coronaryAge = coronaryAge.toUpperCase();
        switch (coronaryAge) {
            case "A"://20-34
            case "B"://35-39
                return 0.5;
            case "C"://40-44
                return 1.0;
            case "D"://45-49
                return 2.0;
            case "E"://50-54
                return 4.0;
            case "F"://55-59
                return 6.0;
            case "G"://60-64
                return 12.0;
            case "H"://65-69
                return 16.0;
            case "I"://70—74
                return 20.0;
            case "J"://75-79
                return 25.0;
            default:
        }
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    //根据总胆固醇（TC）（mmol/L）计算冠心病风险值
    private Double getCoronaryValueByTc(Double tc) {
        Double[] tcs = {7.25, 6.22, 5.18};
        Double[] coronaryValue = {2.0, 1.5, 1.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (tc >= tcs[i]) {
                return coronaryValue[i];
            }
        }
        return 0.0;//tc小于5.18，风险值为0
    }

    private Double getCoronaryValueByTc(String tc) {
        tc = tc.toUpperCase();
        switch (tc) {
            case "A"://<5.18
                return 0.0;
            case "B"://5.18-6.21
                return 1.0;
            case "C"://6.22-7.24
                return 1.5;
            case "D"://>=7.25
                return 2.0;
            default:
        }
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    //根据是否吸烟计算冠心病风险值
    private Double getCoronaryValueBySmoke(Boolean coronarySmoke) {
        return coronarySmoke ? 4.5 : 0.0;//吸烟的风险值为4.5
    }

    //根据高密度脂蛋白（HDL-C）计算冠心病风险值
    private Double getCoronaryValueByHdlC(Double hdlC) {
        //HDL-C小于1.04，风险值为1
        return hdlC < 1.04 ? 1.0 : 0.0;
    }

    private Double getCoronaryValueByHdlC(String hdlC) {
        hdlC = hdlC.toUpperCase();
        switch (hdlC) {
            case "A"://<1.04
                return 1.0;
            case "B"://>=1.04
                return 0.0;
            default:
        }
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    //根据收缩压（SBP）（mmHg）计算冠心病风险值
    private Double getCoronaryValueBySbp(Double sbp) {
        Double[] sbps = {160.0, 140.0, 130.0};
        Double[] coronaryValue = {3.0, 2.0, 1.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (sbp >= sbps[i]) {
                return coronaryValue[i];
            }
        }
        return 0.0;//sbp小于等于129，风险值为0
    }

    private Double getCoronaryValueBySbp(String sbp) {
        sbp = sbp.toUpperCase();
        switch (sbp) {
            case "A"://<129
                return 0.0;
            case "B"://130-139
                return 1.0;
            case "C"://140-159
                return 2.0;
            case "D"://>=160
                return 3.0;
            default:
        }
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    /**
     * 发送体重状况短信
     *
     * @param uOpenid
     * @param coronaryStatus
     */
    private void sendSms(String uOpenid, Integer coronaryStatus, String location) {
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(uOpenid);
            // 获取亲属电话号码列表
            List<Family> familyList = familyDao.select(uOpenid);
            List<String> phoneList = familyList.stream()
                    .map(Family::getPhone).collect(Collectors.toList());
            // 获取亲属电话号码列表
            List<String> openidList = familyList.stream()
                    .map(Family::getFOpenid).collect(Collectors.toList());
            // 发送短信通知家属冠心病高危评估状况
            if (coronaryStatus.equals(CoronaryStatusEnum.HIGH.getCode())) {
                smsService.sendHealthySms(
                        phoneList,// 发送亲属电话号码列表
                        wxMpUser.getNickname(), // 用户微信昵称
                        TweetsTypeEnum.CORONARY.getMsg().concat(EnumUtil.getByCode(coronaryStatus, CoronaryStatusEnum.class).getMsg())); //发送消息
            }
            // 发送微信模板消息通知家属冠心病中危评估状况
            BodyIndexVO bodyIndexVO = new BodyIndexVO();
            bodyIndexVO.setOpenid(openidList);
            bodyIndexVO.setNickname(wxMpUser.getNickname());
            bodyIndexVO.setLocation(location);
            bodyIndexVO.setType(TweetsTypeEnum.CORONARY.getMsg());
            bodyIndexVO.setLevel(EnumUtil.getByCode(coronaryStatus, CoronaryStatusEnum.class).getMsg());
            bodyIndexVO.setPhone(userDao.selectPhoneByOpenid(uOpenid));
            weChatMpService.sendWxMpTempMsg(bodyIndexVO);
        } catch (Exception e) {
            log.error("【短信通知】体重短信通知失败，e = {}", e);
        }
    }
}
