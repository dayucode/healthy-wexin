package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:55 星期四
 * @Description :
 * 用户表
 */
@Slf4j
@Data
@TableName("h_user")
public class User {
    @TableId
    //用户ID
    private String userId;
    //用户微信openid
    private String openid;
    //用户性别
    private Integer userSex;
    //用户生日
    private Date birthday;
    //用户身高
    private Double height;
    //用户体重
    private Double weight;
    //用户手机号
    private String userPhone;
    //用户邮箱
    private String mail;
    //微信昵称
    private String nickname;
    //微信头像
    private String headImgUrl;
    //创建时间c
    private Date createTime;
    //更新时间
    private Date updateTime;
}
