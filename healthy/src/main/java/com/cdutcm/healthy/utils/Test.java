package com.cdutcm.healthy.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 14:47 星期一
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        byte[] encode = Base64.getEncoder().encode("123456".getBytes());
        byte[] decode = Base64.getDecoder().decode(encode);

    }
}
