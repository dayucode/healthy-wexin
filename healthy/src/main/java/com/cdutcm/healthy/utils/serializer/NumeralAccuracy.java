package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 23:45 星期日
 * @Description :
 * 保留一位小数
 */
@Slf4j
public class NumeralAccuracy extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeNumber(Math.round(value * 10) / 10.0);
        }catch (Exception e){
            log.error("【保留小数错误】value = {}", value);
            throw new HealthyException(ResultEnum.NUMERAL_ACCURACY_ERROR);
        }

    }
}
