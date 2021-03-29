package com.cdutcm.healthy.enums;

import lombok.Getter;

import java.security.SecureRandom;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum ObesityTypeEnum implements CodeEnum {
    LIGHTER(0, "偏轻"),
    HEALTHY(1, "健康"),
    OVERWEIGHT(2, "超重"),
    OBESITY(3, "肥胖"),;
    private Integer code;
    private String msg;

    ObesityTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
