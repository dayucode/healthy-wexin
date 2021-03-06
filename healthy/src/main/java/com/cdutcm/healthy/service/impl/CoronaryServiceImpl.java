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
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/26 0:01 ζζδΊ
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
        coronary.setCoronaryValue(getCoronaryValue(coronaryForm));//θ?Ύη½?ι£ι©εΌ
        Integer coronaryStatus = getCoronaryStatus(coronary.getCoronaryValue());
        coronary.setCoronaryStatus(coronaryStatus);//θ?Ύη½?ι£ι©η­ηΊ§
        String location = redisOperator.get(String.format(RedisConstant.USER_LOCATION, openid));
        coronary.setLocation(location);// η¨ζ·ζ΅ιε°ηδ½η½?
        try {
            coronaryDao.insertCoronary(coronary);
        } catch (Exception e) {
            log.error("γε εΏηι£ι©θ―δΌ°γδΏε­θ―δΌ°ζ°ζ?ε€±θ΄₯οΌcoronary = {}", coronary);
            throw new HealthyException(ResultEnum.CORONARY_SAVE_ERROR);
        }
        // ε εΏηζ΅ιη»ζ,ειη­δΏ‘ιη₯
        sendSms(openid, coronaryStatus, location);
        return Entity2VO.convert(coronary, CoronaryVO.class);
    }

    //ιθΏε εΏηι£ι©εΌθ·εε εΏηι£ι©η­ηΊ§
    private Integer getCoronaryStatus(Double coronaryValue) {
        Long coronaryValueRound = Math.round(coronaryValue);//ι£ι©εΌδΈΊε°ζ°οΌεεθδΊε₯εζ΄
        Integer[] coronaryValues = {10, 20, 30};
        CoronaryStatusEnum[] coronaryStatusEnums = {CoronaryStatusEnum.LOW, CoronaryStatusEnum.Middle, CoronaryStatusEnum.HIGH};
        for (int i = 0; i < coronaryStatusEnums.length; i++) {
            if (coronaryValueRound <= coronaryValues[i]) {
                return coronaryStatusEnums[i].getCode();
            }
        }
        return CoronaryStatusEnum.HIGH.getCode();//ι£ι©εΌθΆθΏ30%οΌεεε30%
    }

    //ζ Ήζ?CoronaryFormθ?‘η?ε εΏηι£ι©εΌ
    private Double getCoronaryValue(CoronaryForm coronaryForm) {
        Calendar calendar = new GregorianCalendar();
        Double coronaryValue = 0.0;
        //1.ζ§ε«
        //2.εΉ΄ιΎ
        coronaryValue += getCoronaryValueByAge(coronaryForm.getCoronaryAge());
        //3.ζ»θεΊιοΌTCοΌοΌmmol/LοΌ
        coronaryValue += getCoronaryValueByTc(coronaryForm.getTc());
        //4.ζ―ε¦εΈη
        coronaryValue += getCoronaryValueBySmoke(coronaryForm.getCoronarySmoke());
        //5.ι«ε―εΊ¦θθη½οΌHDL-CοΌ
        coronaryValue += getCoronaryValueByHdlC(coronaryForm.getHdlC());
        //6.ζΆηΌ©εοΌSBPοΌοΌmmHgοΌ
        coronaryValue += getCoronaryValueBySbp(coronaryForm.getSbp());
        return coronaryValue;
    }

    /**
     * ζ Ήζ?εΉ΄ιΎθ?‘η?ε εΏηι£ι©εΌ
     *
     * @param coronaryAge ε εΏηεΉ΄ιΎ
     * @return ε εΏηι£ι©εΌ
     */
    private Double getCoronaryValueByAge(Integer coronaryAge) {
        Integer[] ages = {34, 39, 44, 49, 54, 59, 64, 69, 74, 79};
        Double[] coronaryValue = {0.5, 0.5, 1.0, 2.0, 4.0, 6.0, 12.0, 16.0, 20.0, 25.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (coronaryAge <= ages[i]) {
                return coronaryValue[i];
            }
        }
        return 25.0;//ε€§δΊ79ε²οΌι£ι©εΌεδΈΊ25
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
            case "I"://70β74
                return 20.0;
            case "J"://75-79
                return 25.0;
            default:
        }
        throw new HealthyException(ResultEnum.PARAM_ERROR);
    }

    //ζ Ήζ?ζ»θεΊιοΌTCοΌοΌmmol/LοΌθ?‘η?ε εΏηι£ι©εΌ
    private Double getCoronaryValueByTc(Double tc) {
        Double[] tcs = {7.25, 6.22, 5.18};
        Double[] coronaryValue = {2.0, 1.5, 1.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (tc >= tcs[i]) {
                return coronaryValue[i];
            }
        }
        return 0.0;//tcε°δΊ5.18οΌι£ι©εΌδΈΊ0
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

    //ζ Ήζ?ζ―ε¦εΈηθ?‘η?ε εΏηι£ι©εΌ
    private Double getCoronaryValueBySmoke(Boolean coronarySmoke) {
        return coronarySmoke ? 4.5 : 0.0;//εΈηηι£ι©εΌδΈΊ4.5
    }

    //ζ Ήζ?ι«ε―εΊ¦θθη½οΌHDL-CοΌθ?‘η?ε εΏηι£ι©εΌ
    private Double getCoronaryValueByHdlC(Double hdlC) {
        //HDL-Cε°δΊ1.04οΌι£ι©εΌδΈΊ1
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

    //ζ Ήζ?ζΆηΌ©εοΌSBPοΌοΌmmHgοΌθ?‘η?ε εΏηι£ι©εΌ
    private Double getCoronaryValueBySbp(Double sbp) {
        Double[] sbps = {160.0, 140.0, 130.0};
        Double[] coronaryValue = {3.0, 2.0, 1.0};
        for (int i = 0; i < coronaryValue.length; i++) {
            if (sbp >= sbps[i]) {
                return coronaryValue[i];
            }
        }
        return 0.0;//sbpε°δΊη­δΊ129οΌι£ι©εΌδΈΊ0
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
     * ειδ½ιηΆε΅η­δΏ‘
     *
     * @param uOpenid
     * @param coronaryStatus
     */
    private void sendSms(String uOpenid, Integer coronaryStatus, String location) {
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(uOpenid);
            // θ·εδΊ²ε±η΅θ―ε·η εθ‘¨
            List<Family> familyList = familyDao.select(uOpenid);
            List<String> phoneList = familyList.stream()
                    .map(Family::getPhone).collect(Collectors.toList());
            // θ·εδΊ²ε±η΅θ―ε·η εθ‘¨
            List<String> openidList = familyList.stream()
                    .map(Family::getFOpenid).collect(Collectors.toList());
            // ειη­δΏ‘ιη₯ε?Άε±ε εΏηι«ε±θ―δΌ°ηΆε΅
            if (coronaryStatus.equals(CoronaryStatusEnum.HIGH.getCode())) {
                smsService.sendHealthySms(
                        phoneList,// ειδΊ²ε±η΅θ―ε·η εθ‘¨
                        wxMpUser.getNickname(), // η¨ζ·εΎ?δΏ‘ζ΅η§°
                        TweetsTypeEnum.CORONARY.getMsg().concat(EnumUtil.getByCode(coronaryStatus, CoronaryStatusEnum.class).getMsg())); //ειζΆζ―
            }
            // ειεΎ?δΏ‘ζ¨‘ζΏζΆζ―ιη₯ε?Άε±ε εΏηδΈ­ε±θ―δΌ°ηΆε΅
            BodyIndexVO bodyIndexVO = new BodyIndexVO();
            bodyIndexVO.setOpenid(openidList);
            bodyIndexVO.setNickname(wxMpUser.getNickname());
            bodyIndexVO.setLocation(location);
            bodyIndexVO.setType(TweetsTypeEnum.CORONARY.getMsg());
            bodyIndexVO.setLevel(EnumUtil.getByCode(coronaryStatus, CoronaryStatusEnum.class).getMsg());
            bodyIndexVO.setPhone(userDao.selectPhoneByOpenid(uOpenid));
            weChatMpService.sendWxMpTempMsg(bodyIndexVO);
        } catch (Exception e) {
            log.error("γη­δΏ‘ιη₯γδ½ιη­δΏ‘ιη₯ε€±θ΄₯οΌe = {}", e);
        }
    }
}
