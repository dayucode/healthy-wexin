package com.cdutcm.healthy.properties;

import lombok.Data;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/24 16:58 星期日
 * @Description :
 */
@Data
public class PageProperties {
    private long page = 1;
    private long size = 10;
}
