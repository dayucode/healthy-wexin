package com.cdutcm.healthy.config;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/6 17:30 星期三
 * @Description :
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    //获取当前服务器的URL地址
    public String getUrl() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return "http://" + address.getHostAddress() + ":" + this.serverPort;
        } catch (UnknownHostException e) {
            throw new HealthyException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }
}
