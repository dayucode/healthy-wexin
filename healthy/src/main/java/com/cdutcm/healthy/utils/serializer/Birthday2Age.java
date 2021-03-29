package com.cdutcm.healthy.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 23:44 星期二
 * @Description :
 */
public class Birthday2Age extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 当前日期
        Calendar now = Calendar.getInstance();
        // 生日
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(value);
        // 计算生日与当前日期的年份。相减
        gen.writeNumber(now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR));
    }
}
