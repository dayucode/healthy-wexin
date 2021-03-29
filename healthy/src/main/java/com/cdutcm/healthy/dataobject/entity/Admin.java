package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:45 星期二
 * @Description :
 * 管理员表
 */
@Data
@TableName("h_admin")
public class Admin {
    //管理员ID
    @TableId
    private String adminId;
    // 管理员openid（此管理员必须关注公众号，即在user表中能够查询）
    private String openid;
    //用户名
    private String username;
    //密码
    private String password;
    ////管理员姓名
    private String adminName;
    //管理员性别
    private Integer adminSex;
    //管理员手机号
    private String adminPhone;
    //管理员头像
    private String avatar;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
}
