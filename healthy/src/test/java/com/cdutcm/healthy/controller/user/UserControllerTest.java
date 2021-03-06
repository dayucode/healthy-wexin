package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends HealthyApplicationTests {

    public static final String token = "2701f31e-94ee-46dd-b7ba-f48d928b5b5e";

    @Test
    public void userinfo() {
    }

    @Test
    public void bodyinfo() {
    }

    @Test
    public void perfect() {
    }

    @Test
    public void bindinfo() throws Exception {
        String content = mockMvc.perform(
                get("/user/bindinfo")
                        .cookie(new Cookie("token", UserControllerTest.token)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content = " + GsonUtil.prettyPrintingFormat(content));
    }

    @Test
    public void bind() {
    }

    @Autowired
    private RedisOperator redisOperator;

    @Test
    public void verifyToken(){
        String token = "b780b8cb-8cc2-4c03-a885-43d195fa7423";
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        System.out.println("===============================================");
        System.out.println(tokenValue);
    }
}