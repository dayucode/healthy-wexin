package com.cdutcm.healthy.config;

import com.cdutcm.healthy.properties.HealthyProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 16:08 星期一
 * @Description :
 * 配置微信公众号相关信息
 */
@Component
public class WeChatMpConfig {
    private String appId;
    private String appSecret;
    private String token;

    public WeChatMpConfig(HealthyProperties healthyProperties) {
        appId = healthyProperties.getWechat().getMpAppId();
        appSecret = healthyProperties.getWechat().getMpAppSecret();
        token = healthyProperties.getWechat().getToken();
    }

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(appId);
        wxMpInMemoryConfigStorage.setSecret(appSecret);
        wxMpInMemoryConfigStorage.setToken(token);
        return wxMpInMemoryConfigStorage;
    }
}
