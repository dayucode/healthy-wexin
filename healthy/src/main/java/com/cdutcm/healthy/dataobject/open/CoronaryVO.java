package com.cdutcm.healthy.dataobject.open;

import com.cdutcm.healthy.utils.serializer.Date2Integer;
import com.cdutcm.healthy.utils.serializer.Sex2String;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 23:03 星期二
 * @Description :
 */
@Data
public class CoronaryVO {
    @JsonSerialize(using = Date2Integer.class)
    private Date datetime;
    private String tc;
    private Boolean smoke;
    private String hdlC;
    private String sbp;
    private String location;
    @JsonSerialize(using = Sex2String.class)
    private Integer sex;
    private String age;
}
