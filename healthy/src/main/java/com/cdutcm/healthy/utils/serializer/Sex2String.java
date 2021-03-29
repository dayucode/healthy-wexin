package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.SexEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.utils.EnumUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 21:41 星期二
 * @Description :
 */
@Slf4j
public class Sex2String extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            gen.writeString(EnumUtil.getByCode(value, SexEnum.class).getMsg());
        } catch (Exception e) {
            log.error("【性别类型转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.SEX_TYPE_CONVERT_ERROR);
        }
    }
}
