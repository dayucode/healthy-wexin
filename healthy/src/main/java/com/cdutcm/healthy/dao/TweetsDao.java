package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Tweets;
import com.cdutcm.healthy.dataobject.mapper.TweetsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:38 星期四
 * @Description :
 * 推文表
 */
@Component
public class TweetsDao {
    @Autowired
    private TweetsMapper tweetsMapper;
    public Boolean insertTweets(Tweets tweets) {
        return tweetsMapper.insert(tweets) == 1;
    }

    public Boolean deleteTweetsById(String tweetsId) {
        return tweetsMapper.deleteById(tweetsId) == 1;
    }

    public Boolean deleteTweets(LambdaQueryWrapper<Tweets> queryWrapper) {
        return tweetsMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateTweets(Tweets tweets) {
        return tweetsMapper.updateById(tweets) == 1;
    }

    public Tweets selectById(String tweetsId) {
        return tweetsMapper.selectById(tweetsId);
    }

    public Tweets selectOne(LambdaQueryWrapper<Tweets> queryWrapper) {
        return tweetsMapper.selectOne(queryWrapper);
    }

    public IPage<Tweets> selectPage(IPage<Tweets> page, LambdaQueryWrapper<Tweets> queryWrapper) {
        return tweetsMapper.selectPage(page, queryWrapper);
    }
}
