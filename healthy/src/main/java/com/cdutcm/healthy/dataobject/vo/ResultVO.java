package com.cdutcm.healthy.dataobject.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 20:39 星期四
 * @Description :
 */
@Data
@JsonView(BaseView.class)
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -5226493371743145043L;
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*具体内容*/
    private T data;
}
