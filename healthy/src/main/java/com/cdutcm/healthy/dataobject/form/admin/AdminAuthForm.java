package com.cdutcm.healthy.dataobject.form.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/26 20:22 星期二
 * @Description :
 */
@Data
public class AdminAuthForm {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
