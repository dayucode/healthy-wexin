package com.cdutcm.healthy.dataobject.form.user;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 18:54 星期日
 * @Description :
 * 记录体重
 */
@Data
@Slf4j
public class ObesityForm {
    @ApiModelProperty("测量时间，格式：yyyy-MM-dd HH:mm")
    @NotBlank(message = "时间不能为空")
    private String datetime;
    @ApiModelProperty("测量体重")
    @Min(value = 0, message = "体重必须大于0")
    private Double obesityValue;

    public Date getObesityDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(datetime);
        } catch (Exception e) {
            log.error("【记录体重】时间格式错误，datetime = {}", datetime);
            throw new HealthyException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }
}
