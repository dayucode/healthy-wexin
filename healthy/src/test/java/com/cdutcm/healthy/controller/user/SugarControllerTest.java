package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.HealthyApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

public class SugarControllerTest extends HealthyApplicationTests {

    @Test
    public void record() {
    }

    @Test
    public void measure() {
    }

    @Test
    public void newly() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/sugar/newly")
                        .cookie(new Cookie("token", "tokenvalue"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void census() {
    }

    @Test
    public void history() {
    }
}