package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:38 星期四
 * @Description :
 * 冠心病评估表
 */
@Data
@TableName("h_coronary")
public class Coronary {
    //问卷评估ID
    @TableId
    private String coronaryId;
    //用户微信openid
    private String openid;
    //问卷性别
    private Integer coronarySex;
    //问卷年龄
    private String coronaryAge;
    //总胆固醇（TC）
    private String tc;
    //是否吸烟
    private Boolean coronarySmoke;
    //高密度脂蛋白（HDL-C）
    private String hdlC;
    //收缩压（SBP）（mmHg）
    private String sbp;
    //风险值
    private Double coronaryValue;
    //风险等级
    private Integer coronaryStatus;
    // 用户地理位置
    private String location;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
