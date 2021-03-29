package com.cdutcm.healthy.exception;

import com.cdutcm.healthy.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 20:48 星期四
 * @Description :
 */
@Getter
public class HealthyException extends RuntimeException {
    private Integer code;

    public HealthyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public HealthyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
