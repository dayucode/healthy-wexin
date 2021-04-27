package com.cdutcm.healthy.service;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/4/9 17:28 星期二
 * @Description :
 * 高德地图Service
 */
public interface AmapService {
    // 逆地理编码。根据经纬度获取地理位置
    String regeo(String location);
}
