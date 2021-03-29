package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.CoronaryStatusEnum;
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
 * @Create : 2019/2/26 16:04 星期二
 * @Description :
 */
@Slf4j
public class CoronaryStatus2String extends JsonSerializer<Integer>{
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeString(EnumUtil.getByCode(value, CoronaryStatusEnum.class).getMsg());
        } catch (Exception e) {
            log.error("【冠心病风险评估结果转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.PRESSURE_TYPE_CONVERT_ERROR);
        }
    }
}
