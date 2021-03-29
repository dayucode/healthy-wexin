package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/9 10:09 星期二
 * @Description :
 */
@Data
@TableName("h_family")
public class Family {
    //用户微信openid
    @TableId
    private String uOpenid;
    //用户家属电话
    @TableId
    private String phone;
    //家属微信openid
    @TableId
    private String fOpenid;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public Family() {
    }

    public Family(String uOpenid, String phone, String fOpenid) {
        this.uOpenid = uOpenid;
        this.phone = phone;
        this.fOpenid = fOpenid;
    }
}
