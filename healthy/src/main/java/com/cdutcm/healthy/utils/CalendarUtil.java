package com.cdutcm.healthy.utils;

import java.util.Calendar;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 20:14 星期日
 * @Description :
 */
public class CalendarUtil {
    //获取距离当前时间多久的日期Calendar
    public static Calendar getIntervalNow(int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.add(field, amount);
        return calendar;
    }
}
