package com.cdutcm.healthy.utils;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class Base64UtilTest {

    @Test
    public void encode() {
        /*
        oYnw56OEcTV8oekci1lk-ss-YvoQ
        oYnw56Owyfxpc4V-3qpzSiFVpLmI
        oYnw56KdVE3Q_866jxNVX9nBN6G0
        */
        String encode = Base64Util.encode("oYnw56Owyfxpc4V-3qpzSiFVpLmI");
        System.out.println("encode = " + encode);
    }

    @Test
    public void decode() throws Exception {
        // 4d54497a4e445532
        // MTIzNDU2
        String decode = Base64Util.decode("b1ludzU2T3d5ZnhwYzRWLTNxcHpTaUZWcExtSQ==");
        System.out.println("decode = " + decode);
    }
}