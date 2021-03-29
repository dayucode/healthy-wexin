package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.utils.serializer.Datetime2String;
import com.cdutcm.healthy.utils.serializer.NumeralAccuracy;
import com.cdutcm.healthy.utils.serializer.ObesityType2String;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 19:02 星期日
 * @Description :
 */
@Data
public class ObesityVO {
    @JsonSerialize(using = ObesityType2String.class)
    private Integer obesityType;
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double bmi;
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double obesityValue;
    @JsonProperty("datetime")
    @JsonSerialize(using = Datetime2String.class)
    private Date obesityDatetime;
}
