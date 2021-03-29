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
 * @Create : 2019/2/26 16:11 星期二
 * @Description :
 */
@Slf4j
public class CoronaryValue extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            if (value > 30) {
                value = 30.0;
            }
            gen.writeNumber(Math.round(value * 10) / 10.0);
        } catch (Exception e) {
            log.error("【冠心病风险值转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.CORONARY_VALUE_CONVERT_ERROR);
        }
    }
}
