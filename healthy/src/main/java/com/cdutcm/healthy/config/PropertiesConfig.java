package com.cdutcm.healthy.config;

import com.cdutcm.healthy.properties.HealthyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 17:00 星期日
 * @Description :
 */
@Configuration
@EnableConfigurationProperties(HealthyProperties.class)
public class PropertiesConfig {
}
