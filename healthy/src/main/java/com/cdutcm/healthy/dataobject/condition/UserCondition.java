package com.cdutcm.healthy.dataobject.condition;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 23:01 星期二
 * @Description :
 */
@Data
public class UserCondition {
    @ApiModelProperty("微信昵称")
    private String nickname;
    @ApiModelProperty("用户手机号")
    private String userPhone;
    @ApiModelProperty("用户邮箱")
    private String mail;
}
