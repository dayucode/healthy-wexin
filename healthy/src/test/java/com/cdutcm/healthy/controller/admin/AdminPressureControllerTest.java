package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminPressureControllerTest extends HealthyApplicationTests {

    private String token = "114405e9-35d2-448b-bfd3-140d45da2fb5";

    @Test
    public void history() throws Exception {
        String content = mockMvc.perform(
                get("/admin/pressure/history")
                        .param("openid","oYnw56OEcTV8oekci1lk-ss-YvoQ")
                        .param("size","1000")
                        .cookie(new Cookie("token", token)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content = " + GsonUtil.prettyPrintingFormat(content));
    }
}