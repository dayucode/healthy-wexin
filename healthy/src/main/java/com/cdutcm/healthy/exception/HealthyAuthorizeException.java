package com.cdutcm.healthy.exception;

import com.cdutcm.healthy.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/3/26 20:45 星期二
 * @Description :
 * 登录授权异常
 */
@Getter
public class HealthyAuthorizeException extends RuntimeException {
    private Integer code;

    public HealthyAuthorizeException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
