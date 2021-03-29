package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.form.admin.TweetsForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.TweetsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 17:52 星期二
 * @Description :
 * 推文
 */
public interface TweetsService {
    //热点推文
    IPageVO hotspotTweets(PageForm page);

    //根据推文类型获取推文列表
    IPageVO getTweetsList(String tweetsType, PageForm page);

    //根据推文ID获取推文
    TweetsVO getTweets(String tweetsId);

    //上传文件
    List<String> uploadFile(List<MultipartFile> files);

    //删除推文
    void deleteTweets(String tweetsId);

    //修改推文
    void modifyTweets(TweetsForm tweetsForm);

    //保存推文
    void saveTweets(TweetsForm tweetsForm);
}
