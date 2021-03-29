package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 16:55 星期二
 * @Description :
 */
@Getter
public enum TweetsTypeEnum implements CodeEnum {
    HOTSPOT(0, "热点"),
    PRESSURE(1, "高血压"),
    SUGAR(2, "高血糖"),
    OBESITY(3, "肥胖症"),
    CORONARY(4, "冠心病"),;

    private Integer code;
    private String msg;

    TweetsTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
