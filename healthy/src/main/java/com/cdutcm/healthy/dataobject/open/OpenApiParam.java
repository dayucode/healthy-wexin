package com.cdutcm.healthy.dataobject.open;

import com.cdutcm.healthy.enums.SexEnum;
import com.cdutcm.healthy.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 13:20 星期二
 * @Description :
 */
@Data
public class OpenApiParam {
    @ApiModelProperty(value = "开放KEY",example = "oYnw56OEcTV8oekci1lk-ss-YvoQ")
    @NotBlank(message = "key不能为空")
    private String key;
    @ApiModelProperty(value = "测量日期",example = "2019-04-05")
    private String datetime;
    @ApiModelProperty(value = "性别",example = "男")
    private String sex;
    @ApiModelProperty(value = "年龄",example = "74")
    private Integer age;
    @ApiModelProperty(value = "省份",example = "四川")
    private String province;
    @ApiModelProperty(value = "城市",example = "成都")
    private String city;

    public Integer getSex() {
        if (sex == null) {
            return null;
        }
        return EnumUtil.getByMsg(sex, SexEnum.class).getCode();
    }
}
