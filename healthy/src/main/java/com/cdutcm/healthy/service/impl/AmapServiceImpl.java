package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.cdutcm.healthy.dataobject.amap.Regeo;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.service.AmapService;
import com.cdutcm.healthy.utils.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/9 17:32 星期二
 * @Description :
 */
@Service
public class AmapServiceImpl implements AmapService {
    // 高德地图逆地理编码请求URL
    private static final String REGEO_URL =
            "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}";
    private String key;

    public AmapServiceImpl(HealthyProperties healthyProperties) {
        key = healthyProperties.getAmap().getKey();
    }

    @Override
    public String regeo(String location) {
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("location", location);
        RestTemplate restTemplate = new RestTemplate();
        Regeo regeo = restTemplate.getForObject(REGEO_URL, Regeo.class, map);
        return regeo.getRegeocode().getFormatted_address();
    }
}
