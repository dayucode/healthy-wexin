package com.cdutcm.healthy.utils.converter;

import com.cdutcm.healthy.dataobject.entity.Pressure;
import com.cdutcm.healthy.dataobject.vo.user.PressureTendencyVO;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 16:30 星期日
 * @Description :
 */
public class Pressure2PressureTendencyVO {
    public static PressureTendencyVO convert(Pressure pressure) {
        PressureTendencyVO pressureTendencyVO = new PressureTendencyVO();
        BeanUtils.copyProperties(pressure, pressureTendencyVO);//没有转化时间time属性
        SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");//时间转换格式
        pressureTendencyVO.setTime(timeSDF.format(pressure.getPressureDatetime()));//转换时间time，并将time设置到PressureTendencyVO
        return pressureTendencyVO;
    }
}
