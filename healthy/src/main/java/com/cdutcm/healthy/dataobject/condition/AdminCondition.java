package com.cdutcm.healthy.dataobject.condition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/26 22:34 星期二
 * @Description :
 */
@Data
public class AdminCondition {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户姓名")
    private String adminName;
    @ApiModelProperty("用户手机号")
    private String adminPhone;
}
