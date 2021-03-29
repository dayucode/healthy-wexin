package com.cdutcm.healthy.utils;

import com.cdutcm.healthy.exception.HealthyException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/21 14:52 星期日
 * @Description :
 */
public class Base64Util {

    /**
     * 加密字符串
     *
     * @param str 明文
     * @return 密文
     */
    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 解密字符串
     *
     * @param str 密文
     * @return 明文
     * @throws Exception 解密异常信息
     */
    public static String decode(String str) throws Exception {
        byte[] decode = Base64.getDecoder().decode(str);
        return new String(decode);
    }
}
