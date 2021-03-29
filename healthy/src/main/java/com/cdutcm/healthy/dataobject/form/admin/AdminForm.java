package com.cdutcm.healthy.dataobject.form.admin;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.SexEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:45 星期二
 * @Description :
 */
@Slf4j
@Data
public class AdminForm {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("管理员姓名")
    @NotBlank(message = "姓名不能为空")
    private String adminName;
    @ApiModelProperty("管理员性别")
    @NotBlank(message = "性别不能为空")
    private String adminSex;
    @ApiModelProperty("管理员手机号")
    @NotBlank(message = "手机号不能为空")
    private String adminPhone;

    public Integer getAdminSex() {
        try {
            return EnumUtil.getByMsg(adminSex, SexEnum.class).getCode();
        } catch (Exception e) {
            log.error("【性别类型转换错误】adminSex = {}", adminSex);
            throw new HealthyException(ResultEnum.SEX_TYPE_CONVERT_ERROR);
        }
    }
}
