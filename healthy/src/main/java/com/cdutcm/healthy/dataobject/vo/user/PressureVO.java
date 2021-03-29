package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.utils.serializer.Datetime2String;
import com.cdutcm.healthy.utils.serializer.PressureType2String;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 20:37 星期六
 * @Description :
 * 首页-血压：显示最近一次测量记录的数据
 */
@Data
public class PressureVO {
    @JsonSerialize(using = PressureType2String.class)
    private Integer pressureType;
    private Double highPressure;
    private Double lowPressure;
    @JsonProperty("datetime")
    @JsonSerialize(using = Datetime2String.class)
    private Date pressureDatetime;
}
