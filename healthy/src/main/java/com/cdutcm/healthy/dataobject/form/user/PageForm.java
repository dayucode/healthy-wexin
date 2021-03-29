package com.cdutcm.healthy.dataobject.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 16:56 星期日
 * @Description :
 */
@Data
public class PageForm {
    @ApiModelProperty("分页-当前页数（默认为1）")
    private long page = 1;
    @ApiModelProperty("分页-每页数量（默认为10）")
    private long size = 10;
}
