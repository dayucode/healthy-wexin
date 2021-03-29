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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 23:16 星期一
 * @Description :
 */
@Data
@Slf4j
public class UserForm {
    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
    private String userSex;
    @ApiModelProperty("出生日期，格式：yyyy-MM-dd")
    @NotBlank(message = "出生日期不能为空")
    private String birthday;
    @ApiModelProperty("身高")
    @NotNull(message = "身高不能为空(cm)")
    private Double height;
    @ApiModelProperty("体重")
    @NotNull(message = "体重不能为空(kg)")
    private Double weight;


    public Integer getUserSex() {
        try {
            return EnumUtil.getByMsg(userSex, SexEnum.class).getCode();
        } catch (Exception e) {
            log.error("【性别类型转换错误】userSex = {}", userSex);
            throw new HealthyException(ResultEnum.SEX_TYPE_CONVERT_ERROR);
        }
    }

    public Date getBirthday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(birthday);
        } catch (Exception e) {
            log.error("【完善基本信息】时间格式错误，birthday = {}", birthday);
            throw new HealthyException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }
}
