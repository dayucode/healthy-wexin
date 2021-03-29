package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum SugarTypeEnum implements CodeEnum {
    LOW(0, "低血糖"),
    NORMAL(1, "正常"),
    DAMAGE(2, "受损"),
    SERIOUS(3, "重度"),;
    private Integer code;
    private String msg;

    SugarTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
