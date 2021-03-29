package com.cdutcm.healthy.dataobject.vo.admin;

import com.cdutcm.healthy.utils.serializer.Sex2String;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 21:39 星期二
 * @Description :
 */
@Data
public class AdminVO {
    private String adminId;
    private String username;
    private String adminName;
    @JsonSerialize(using = Sex2String.class)
    private Integer adminSex;
    private String adminPhone;
}
