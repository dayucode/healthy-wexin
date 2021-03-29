package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:58 星期四
 * @Description :
 * 体重表
 */
@Data
@TableName("h_obesity")
public class Obesity {
    //体重测量ID
    @TableId
    private String obesityId;
    //用户微信openid
    private String openid;
    //体重测量时间
    private Date obesityDatetime;
    //体重值
    private Double obesityValue;
    //BMI指数
    private Double bmi;
    //体重状态
    private Integer obesityType;
    // 用户地理位置
    private String location;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
