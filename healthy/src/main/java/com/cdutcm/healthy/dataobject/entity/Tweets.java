package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:46 星期四
 * @Description :
 * 推文表
 */
@Data
@TableName("h_tweets")
public class Tweets {
    //推文ID
    @TableId
    private String tweetsId;
    //推文标题
    private String tweetsTitle;
    //推文类型
    private Integer tweetsType;
    //推文简介
    private String tweetsSynopsis;
    //推文正文
    private String tweetsText;
    //推文浏览量
    private Integer tweetsAmount;
    //推文图片
    private String tweetsImg;
    //推文状态
    private Boolean tweetsStatus;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public void addTweetsAmount() {
        this.tweetsAmount += 1;
    }
}
