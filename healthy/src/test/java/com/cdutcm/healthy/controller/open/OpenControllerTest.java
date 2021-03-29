package com.cdutcm.healthy.controller.open;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class OpenControllerTest extends HealthyApplicationTests {

    @Test
    public void pressure() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", "b1ludzU2T3d5ZnhwYzRWLTNxcHpTaUZWcExtSQ==");
        String content = mockMvc.perform(
                get("/open/pressure")
                        .params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("content = " + GsonUtil.prettyPrintingFormat(content));
    }

    @Test
    public void sugar() {
    }

    @Test
    public void obesity() {
    }

    @Test
    public void coronary() {
    }
}