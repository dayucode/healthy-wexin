package com.cdutcm.healthy.utils;

import com.cdutcm.healthy.enums.CodeEnum;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 19:46 星期六
 * @Description :
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static <T extends CodeEnum> T getByMsg(String msg, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if (msg.equals(e.getMsg())) {
                return e;
            }
        }
        return null;
    }

    public static <T extends CodeEnum> T getByName(String name, Class<T> enumClass) {
        for (T e : enumClass.getEnumConstants()) {
            if (name.toUpperCase().equals(e.toString())) {
                return e;
            }
        }
        return null;
    }
}
