package com.cdutcm.healthy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/6 19:50 星期三
 * @Description :
 * 配置跨域信息
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins(
                        "http://10.200.116.217:5000",
                        "http://healthy.tuyrk.cn",
                        "http://127.0.0.1:5000",
                        "http://localhost:63342"
                )
                .allowedHeaders("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }
}