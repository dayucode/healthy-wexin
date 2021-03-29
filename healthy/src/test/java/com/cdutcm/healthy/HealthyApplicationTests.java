package com.cdutcm.healthy;

import com.cdutcm.healthy.dataobject.form.admin.AdminAuthForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthyApplicationTests {

    @Autowired
    private WebApplicationContext applicationContext;
    protected MockMvc mockMvc;


    @Before
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    private static final String token = "";

    @Test
    public void test() throws Exception {
        //构造添加的用户信息，更新id为2的用户的用户信息
        AdminAuthForm adminAuthForm = new AdminAuthForm();
        adminAuthForm.setUsername("tyk");
        adminAuthForm.setPassword("123456");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                put("")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(adminAuthForm))
                        .cookie(new Cookie("token", token)))
                //判断返回值，是否达到预期，测试示例中的返回值的结构如下
                //{"code":0,"msg":"OK","data":null}
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}
