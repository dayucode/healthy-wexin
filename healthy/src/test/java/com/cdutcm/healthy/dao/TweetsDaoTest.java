package com.cdutcm.healthy.dao;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.dataobject.entity.Tweets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TweetsDaoTest extends HealthyApplicationTests {
    @Autowired
    private TweetsDao tweetsDao;

    @Test
    public void insertTweets() {
        Tweets tweets = new Tweets();
        tweets.setTweetsId("1");
        tweets.setTweetsImg("123.jpg");
        tweets.setTweetsSynopsis("高血压");
        tweets.setTweetsText("高血压");
        tweets.setTweetsTitle("高血压");
        tweets.setTweetsType(1);
        Boolean aBoolean = tweetsDao.insertTweets(tweets);
        System.out.println("aBoolean = " + aBoolean);
    }

    @Test
    public void deleteTweetsById() {
    }

    @Test
    public void deleteTweets() {
    }

    @Test
    public void updateTweets() {
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectPage() {
    }
}