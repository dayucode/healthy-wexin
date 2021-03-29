package com.cdutcm.healthy.dataobject.open;

import com.cdutcm.healthy.utils.serializer.Birthday2Age;
import com.cdutcm.healthy.utils.serializer.Date2Integer;
import com.cdutcm.healthy.utils.serializer.Sex2String;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiResponse;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 13:22 星期二
 * @Description :
 */
@Data
public class PressureVO {
    // 测量时间(1554905055749)
    @JsonSerialize(using = Date2Integer.class)
    private Date datetime;
    // 收缩压，单位mmHg(136)
    private Double systolic;
    // 舒张压，单位mmHg(100)
    private Double diastolic;
    // 测量地理位置(四川省成都市温江区天府街道柳台大道西段1166号成都中医药大学)
    private String location;
    // 性别(男)
    @JsonSerialize(using = Sex2String.class)
    private Integer sex;
    // 年龄(74)
    @JsonSerialize(using = Birthday2Age.class)
    @JsonProperty("age")
    private Date birthday;
    // 身高，单位CM(174)
    private Double height;
    // 体重，单位Kg(75)
    private Double weight;
}
