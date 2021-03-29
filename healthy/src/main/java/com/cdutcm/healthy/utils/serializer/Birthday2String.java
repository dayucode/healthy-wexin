package com.cdutcm.healthy.utils.serializer;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 23:44 星期二
 * @Description :
 */
@Slf4j
public class Birthday2String extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            gen.writeString(sdf.format(value));
        }catch (Exception e){
            log.error("【时间格式转换错误】value = {}", value);
            throw new HealthyException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }
}
