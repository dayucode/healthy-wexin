package com.cdutcm.healthy.dataobject.open;

import com.cdutcm.healthy.utils.serializer.Birthday2Age;
import com.cdutcm.healthy.utils.serializer.Date2Integer;
import com.cdutcm.healthy.utils.serializer.Sex2String;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 21:51 星期二
 * @Description :
 */
@Data
public class SugarVO {
    @JsonSerialize(using = Date2Integer.class)
    private Date datetime;
    private Double sugarValue;
    private String location;
    @JsonSerialize(using = Sex2String.class)
    private Integer sex;
    @JsonSerialize(using = Birthday2Age.class)
    @JsonProperty("age")
    private Date birthday;
    private Double height;
    private Double weight;
}
