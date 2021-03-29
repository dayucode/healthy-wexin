package com.cdutcm.healthy.service;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.dataobject.form.admin.TweetsForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TweetsServiceTest extends HealthyApplicationTests {

    @Autowired
    private TweetsService tweetsService;

    @Test
    public void hotspotTweets() {
    }

    @Test
    public void getTweetsList() {
    }

    @Test
    public void getTweets() {
    }

    @Test
    public void uploadFile() {
    }

    @Test
    public void deleteTweets() {
    }

    @Test
    public void modifyTweets() {
    }

    @Test
    public void saveTweets() {
        TweetsForm tweetsForm = new TweetsForm();
        tweetsForm.setTweetsTitle("123");
        tweetsForm.setTweetsType("冠心病");
        tweetsForm.setTweetsSynopsis("简介");
        tweetsForm.setTweetsText("正文");
        tweetsForm.setTweetsImg("123.jpg");
        tweetsService.saveTweets(tweetsForm);
    }
}