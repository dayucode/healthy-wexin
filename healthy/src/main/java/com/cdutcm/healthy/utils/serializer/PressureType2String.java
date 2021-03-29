package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.PressureTypeEnum;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.utils.EnumUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 21:15 星期六
 * @Description :
 */
@Slf4j
public class PressureType2String extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeString(EnumUtil.getByCode(value, PressureTypeEnum.class).getMsg());
        } catch (Exception e) {
            log.error("【血压类型转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.PRESSURE_TYPE_CONVERT_ERROR);
        }
    }
}
