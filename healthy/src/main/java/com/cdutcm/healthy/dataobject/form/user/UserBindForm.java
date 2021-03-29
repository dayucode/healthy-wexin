package com.cdutcm.healthy.dataobject.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 23:39 星期一
 * @Description :
 */
@Data
public class UserBindForm {
    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String userPhone;
    @ApiModelProperty("邮箱")
    private String mail;
}
