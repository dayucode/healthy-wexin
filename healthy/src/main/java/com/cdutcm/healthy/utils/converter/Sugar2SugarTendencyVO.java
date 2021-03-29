package com.cdutcm.healthy.utils.converter;

import com.cdutcm.healthy.dataobject.entity.Sugar;
import com.cdutcm.healthy.dataobject.vo.user.SugarTendencyVO;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 23:06 星期日
 * @Description :
 */
public class Sugar2SugarTendencyVO {
    public static SugarTendencyVO convert(Sugar sugar) {
        SugarTendencyVO sugarTendencyVO = new SugarTendencyVO();
        BeanUtils.copyProperties(sugar, sugarTendencyVO);//没有转化时间time属性
        SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");//时间转换格式
        sugarTendencyVO.setTime(timeSDF.format(sugar.getSugarDatetime()));//转换时间time，并将time设置到SugarTendencyVO
        return sugarTendencyVO;
    }
}
