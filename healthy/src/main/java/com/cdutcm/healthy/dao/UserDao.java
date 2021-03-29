package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.dataobject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:38 星期四
 * @Description :
 * 用户表
 */
@Component
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public Boolean insertUser(User user) {
        return userMapper.insert(user) == 1;
    }

    public Boolean deleteUserById(String userId) {
        return userMapper.deleteById(userId) == 1;
    }

    public Boolean deleteUser(LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateUser(User user) {
        return userMapper.updateById(user) == 1;
    }

    public User selectById(String userId) {
        return userMapper.selectById(userId);
    }

    public User selectByOpenid(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid);
        return userMapper.selectOne(queryWrapper);
    }

    public String selectPhoneByOpenid(String openid) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getOpenid, openid)
                .select(User::getUserPhone);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        return user.getUserPhone();
    }

    public String selectOpenidByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserPhone, phone)
                .select(User::getOpenid);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        return user.getOpenid();
    }

    public User selectOne(LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.selectOne(queryWrapper);
    }

    public IPage<User> selectPage(IPage<User> page, LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }
}
