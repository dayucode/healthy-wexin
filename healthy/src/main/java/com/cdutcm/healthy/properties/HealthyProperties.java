package com.cdutcm.healthy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 16:58 星期日
 * @Description :
 */
@Data
@ConfigurationProperties(prefix = "healthy")
public class HealthyProperties {
    private PageProperties page = new PageProperties();
    private WeChatAccountProperties wechat = new WeChatAccountProperties();
    private ProjectUrlProperties url = new ProjectUrlProperties();
    private AliyunProperties aliyun = new AliyunProperties();
    private AmapProperties amap = new AmapProperties();
    private AdminProperties admin = new AdminProperties();

}
