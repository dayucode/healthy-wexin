package com.cdutcm.healthy.utils.converter;

import com.cdutcm.healthy.dataobject.entity.Obesity;
import com.cdutcm.healthy.dataobject.vo.user.ObesityTendencyVO;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 23:06 星期日
 * @Description :
 */
public class Obesity2ObesityTendencyVO {
    public static ObesityTendencyVO convert(Obesity obesity) {
        ObesityTendencyVO obesityTendencyVO = new ObesityTendencyVO();
        BeanUtils.copyProperties(obesity, obesityTendencyVO);//没有转化时间time属性
        SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");//时间转换格式
        obesityTendencyVO.setTime(timeSDF.format(obesity.getObesityDatetime()));//转换时间time，并将time设置到ObesityTendencyVO
        return obesityTendencyVO;
    }
}
