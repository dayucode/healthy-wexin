package com.cdutcm.healthy.dataobject.form.user;

import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 20:43 星期四
 * @Description :
 */
@Data
@Slf4j
public class PressureForm {
    @ApiModelProperty("测量时间，格式：yyyy-MM-dd HH:mm")
    @NotBlank(message = "时间不能为空")
    private String datetime;
    @ApiModelProperty("测量血压收缩压")
    @NotNull(message = "收缩压不能为空")
    @Min(value = 0, message = "收缩压必须大于0")
    private Double highPressure;
    @ApiModelProperty("测量血压舒张压")
    @NotNull(message = "舒张压不能为空")
    @Min(value = 0, message = "舒张压必须大于0")
    private Double lowPressure;

    public Date getPressureDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(datetime);
        } catch (Exception e) {
            log.error("【记录血压】时间格式错误，datetime = {}", datetime);
            throw new HealthyException(ResultEnum.TIME_FORMAT_ERROR);
        }
    }
}
