package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest extends HealthyApplicationTests {

    @Test
    public void add() throws Exception {
        String content = mockMvc.perform(
                post("/admin/add")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"adminName\": \"蒋同学\",\n" +
                                "  \"adminPhone\": \"18428305872\",\n" +
                                "  \"adminSex\": \"男\",\n" +
                                "  \"username\": \"okok\"\n" +
                                "}")
                        .cookie(new Cookie("token", "266417b3-10f4-4385-a2b4-5c89115ac0e5")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content = " + GsonUtil.prettyPrintingFormat(content));
    }

    @Test
    public void list() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void reset() {
    }

    @Test
    public void login() {
    }
}