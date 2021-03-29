package com.cdutcm.healthy.utils;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 21:01 星期二
 * @Description :
 * MD5加密
 */
@Slf4j
public class EncryptUtils {
    public static String encrypt(String value, String key) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(key);
        } catch (NoSuchAlgorithmException e) {
            log.error("【加密】加密错误，value = {}，key = {}", value, key);
            throw new HealthyException(ResultEnum.ENCRYPT_TYPE_ERROR);
        }
        messageDigest.update(value.getBytes());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
}