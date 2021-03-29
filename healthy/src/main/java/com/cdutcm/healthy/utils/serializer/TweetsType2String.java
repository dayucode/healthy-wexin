package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.TweetsTypeEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.utils.EnumUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 19:05 星期二
 * @Description :
 */
@Slf4j
public class TweetsType2String extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeString(EnumUtil.getByCode(value, TweetsTypeEnum.class).getMsg());
        } catch (Exception e) {
            log.error("【推文类型转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.TWEETS_TYPE_CONVERT_ERROR);
        }
    }
}
