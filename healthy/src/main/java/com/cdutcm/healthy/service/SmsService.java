package com.cdutcm.healthy.service;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/25 15:22 星期一
 * @Description :
 */
public interface SmsService {
    // 发送验证码
    void sendVerifySms(String to, String code);

    // 发送健康通知
    void sendHealthySms(String to, String name, String type);

    // 发送健康通知
    void sendHealthySms(List<String> to, String name, String type);
}
