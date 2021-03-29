package com.cdutcm.healthy.properties;

import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/25 15:26 星期一
 * @Description :
 */
@Data
public class AliyunProperties {
    private String accessKeyId = "";
    private String secret = "";
    private String signName = "涂元坤";

    private String verifyTemplateCode = "";
    private String healthyTemplateCode = "";
}
