package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 15:46 星期二
 * @Description :
 */
@Getter
public enum CoronaryStatusEnum implements CodeEnum {
    LOW(1, "低危"),
    Middle(2, "中危"),
    HIGH(3, "高危");
    private Integer code;
    private String msg;

    CoronaryStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
