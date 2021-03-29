package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.http.MediaType.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PressureControllerTest extends HealthyApplicationTests {

    private static final String token = "16ce9fa5-b51c-4a4a-b9b1-7877b95a96b3";

    @Test
    @Transactional
    public void record() throws Exception {
        String contentAsString = mockMvc.perform(
                post("/user/pressure/record")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\"datetime\": \"2019-04-06 15:30\", \"highPressure\": 120, \"lowPressure\": 99}")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("成功")))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + GsonUtil.prettyPrintingFormat(contentAsString));
    }

    @Test
    public void measure() throws Exception {
        String contentAsString = mockMvc.perform(
                get("/user/pressure/measure")
                        .param("highPressure", "120")
                        .param("lowPressure", "99")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("成功")))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + GsonUtil.prettyPrintingFormat(contentAsString));

    }

    @Test
    public void newly() throws Exception {
        String contentAsString = mockMvc.perform(
                get("/user/pressure/newly")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("成功")))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + GsonUtil.prettyPrintingFormat(contentAsString));
    }

    @Test
    public void census() throws Exception {
        String contentAsString = mockMvc.perform(
                get("/user/pressure/census")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("成功")))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + GsonUtil.prettyPrintingFormat(contentAsString));
    }

    @Test
    public void history() throws Exception {
        String contentAsString = mockMvc.perform(
                get("/user/pressure/history")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "  \"page\": 1,\n" +
                                "  \"size\": 10\n" +
                                "}")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.msg", is("成功")))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString = " + GsonUtil.prettyPrintingFormat(contentAsString));
    }
}