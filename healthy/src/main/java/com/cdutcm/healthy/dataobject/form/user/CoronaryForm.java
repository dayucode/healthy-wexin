package com.cdutcm.healthy.dataobject.form.user;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.SexEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:38 星期四
 * @Description :
 * 冠心病评估表
 */
@Slf4j
@Data
public class CoronaryForm {
    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
    private String coronarySex;
    @ApiModelProperty("年龄")
    @NotBlank(message = "年龄不能为空")
    private String coronaryAge;
    @ApiModelProperty("总胆固醇（TC）")
    @NotBlank(message = "TC不能为空")
    private String tc;
    @ApiModelProperty("是否吸烟，布尔类型")
    @NotNull(message = "是否吸烟不能为空")
    private Boolean coronarySmoke;
    @ApiModelProperty("高密度脂蛋白（HDL-C）")
    @NotBlank(message = "HDL-C不能为空")
    private String hdlC;
    @ApiModelProperty("收缩压（SBP）（mmHg）")
    @NotBlank(message = "SBP不能为空")
    private String sbp;

    public Integer getCoronarySex() {
        try {
            return EnumUtil.getByMsg(coronarySex, SexEnum.class).getCode();
        } catch (Exception e) {
            log.error("【性别类型转换错误】coronarySex = {}", coronarySex);
            throw new HealthyException(ResultEnum.SEX_TYPE_CONVERT_ERROR);
        }
    }
}
