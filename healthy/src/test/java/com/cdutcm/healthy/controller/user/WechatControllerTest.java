package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.properties.HealthyProperties;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WechatControllerTest extends HealthyApplicationTests {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private HealthyProperties healthyProperties;

    @Test
    public void checkSignature() throws WxErrorException {
        String jsapiTicket = wxMpService.getJsapiTicket();
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(healthyProperties.getUrl().getWechatMpAuthorize());
        System.out.println("jsapiTicket = " + jsapiTicket);//LIKLckvwlJT9cWIhEQTwfMIcP2bK6Q7bGHZzsUjre50bLi1Twj6Xam8tbvg88uAUIIAqXNADEK8rCmyTway1Og
    }
}