package com.cdutcm.healthy.properties;

import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 18:47 星期一
 * @Description :
 */
@Data
public class ProjectUrlProperties {
    // 微信公众平台授权URL
    private String wechatMpAuthorize;
    //登录成功的URL
    private String returnUrl;
    //完善用户信息的URL
    private String perfectUrl;
    //管理员登录URL
    private String adminLogin;
    // 老人健康管理平台
    private String healthy;
    private String domain;
}
