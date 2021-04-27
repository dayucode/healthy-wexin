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
                                "  \"adminName\": \"daYu\",\n" +
                                "  \"adminPhone\": \"15109674440\",\n" +
                                "  \"adminSex\": \"男\",\n" +
                                "  \"username\": \"dayucode\"\n" +
                                "}")
                        .cookie(new Cookie("token", "9d67797a-fb52-4371-a384-3c0a4fb61a5h")))
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