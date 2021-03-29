package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:44 星期四
 * @Description :
 * 血糖表
 */
@Data
@TableName("h_sugar")
public class Sugar {
    //血糖测量ID
    @TableId
    private String sugarId;
    //用户微信openid
    private String openid;
    //血糖测量时间
    private Date sugarDatetime;
    //血糖指数
    private Double sugarValue;
    //血糖类型
    private Integer sugarType;
    // 用户地理位置
    private String location;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
