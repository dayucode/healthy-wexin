package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.dataobject.entity.Tweets;
import com.cdutcm.healthy.dataobject.vo.PageVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class TweetsDaoTest extends HealthyApplicationTests {
    @Autowired
    private TweetsDao tweetsDao;

    @Test
    public void insertTweets() {
        Tweets tweets = new Tweets();
        tweets.setTweetsId("2");
        tweets.setTweetsImg("./img/add.png");
        tweets.setTweetsSynopsis("高血压");
        tweets.setTweetsText("高血压");
        tweets.setTweetsTitle("高血压");
        tweets.setTweetsType(1);
        tweets.setTweetsStatus(false);
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
        Tweets tweets = tweetsDao.selectById("1");
        System.out.println(tweets);
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void selectPage() {
        LambdaQueryWrapper<Tweets> queryWrapper = new LambdaQueryWrapper<Tweets>()
                .eq(Tweets::getTweetsStatus, true);//未删除的推文
        IPage<Tweets> tweetsIPage = tweetsDao.selectPage(new PageVO<>(), queryWrapper);
        List<Tweets> records = tweetsIPage.getRecords();
        System.out.println(records);
    }
}