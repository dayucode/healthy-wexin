package com.cdutcm.healthy.properties;

import lombok.Data;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/3/25 15:26 星期一
 * @Description :
 */
@Data
public class AliyunProperties {
    private String accessKeyId = "";
    private String secret = "";
    private String signName = "daYu";

    private String verifyTemplateCode = "";
    private String healthyTemplateCode = "";
}
