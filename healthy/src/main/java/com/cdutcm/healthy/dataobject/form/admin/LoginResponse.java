package com.cdutcm.healthy.dataobject.form.admin;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**
 * 扫码登录信息承载类
 */
@Data
public class LoginResponse {
	private CountDownLatch latch;
    private String openid;
}
