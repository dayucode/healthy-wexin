package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/14 20:10 星期四
 * @Description :
 */
@Getter
public enum RecordTypeEnum implements CodeEnum {
    HIGH_PRESSURE(0, "收缩压"),
    LOW_PRESSURE(1, "舒张压"),
    SUGAR(2, "血糖"),
    OBESITY(3, "体重");

    private Integer code;
    private String msg;

    RecordTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
