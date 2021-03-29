package com.cdutcm.healthy.dataobject.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cdutcm.healthy.dataobject.vo.BaseView;
import com.cdutcm.healthy.utils.serializer.TweetsType2String;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:46 星期四
 * @Description :
 * 推文表
 */
@Data
@TableName("h_tweets")
public class TweetsVO {
    public interface TweetsPublicView extends BaseView {
    }

    public interface TweetsListView extends TweetsPublicView {
    }

    public interface TweetsDetailView extends TweetsPublicView {
    }


    @JsonView({TweetsListView.class, TweetsPublicView.class})
    private String tweetsId;
    @JsonView(TweetsPublicView.class)
    private String tweetsTitle;
    @JsonView(TweetsListView.class)
    private String tweetsSynopsis;
    @JsonView(TweetsDetailView.class)
    @JsonSerialize(using = TweetsType2String.class)
    private Integer tweetsType;
    @JsonView(TweetsDetailView.class)
    private String tweetsText;
    @JsonView(TweetsListView.class)
    private Integer tweetsAmount;
    @JsonView(TweetsListView.class)
    private String tweetsImg;
}
