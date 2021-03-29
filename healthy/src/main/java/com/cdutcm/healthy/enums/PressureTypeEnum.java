package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum PressureTypeEnum implements CodeEnum {
    /**
     * 血压类型 - 低血压
     */
    LOW(-1, "低血压"),
    /**
     * 血压类型 - 正常
     */
    NORMAL(0, "正常"),
    /**
     * 血压类型 - 正常偏高
     */
    HIGH(2, "正常偏高"),
    /**
     * 血压类型 - 轻度
     */
    LIGHT(3, "轻度"),
    /**
     * 血压类型 - 中度
     */
    MODERATE(4, "中度"),
    /**
     * 血压类型 - 重度
     */
    SERIOUS(5, "重度");
    private Integer code;
    private String msg;

    PressureTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
