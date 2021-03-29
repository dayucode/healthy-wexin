package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;

import javax.servlet.http.Cookie;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminTweetsControllerTest extends HealthyApplicationTests {

    private static final String token = "28278cfa-05d5-4206-a979-72cc565e35ae";

    @Test
    public void list() throws Exception {

    }

    @Test
    public void delete() {
    }

    @Test
    public void detail() {
    }

    @Test
    public void modify() {
    }

    @Test
    public void save() throws Exception {
        String content = mockMvc.perform(
                post("/admin/tweets/save")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"tweetsImg\": \"123.jpg\",\n" +
                                "  \"tweetsSynopsis\": \"高血压\",\n" +
                                "  \"tweetsText\": \"高血压\",\n" +
                                "  \"tweetsTitle\": \"高血压\",\n" +
                                "  \"tweetsType\": \"高血压\"\n" +
                                "}")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content = " + GsonUtil.prettyPrintingFormat(content));

    }

    @Test
    public void editor() {
    }
}