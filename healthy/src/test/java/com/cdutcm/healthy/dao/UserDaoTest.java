package com.cdutcm.healthy.dao;

import com.cdutcm.healthy.HealthyApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserDaoTest extends HealthyApplicationTests {
    @Autowired
    private UserDao userDao;

    @Test
    public void selectPhoneByOpenid() {
        String phone = userDao.selectPhoneByOpenid("oYnw56OEcTV8oekci1lk-ss-YvoQ");
        System.out.println("phone = " + phone);
    }

    @Test
    public void selectOpenidByPhone() {
        String openid = userDao.selectOpenidByPhone("18428305872");
        System.out.println("openid = " + openid);
    }
}