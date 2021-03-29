package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SmsServiceImplTest extends HealthyApplicationTests {

    @Autowired
    private SmsService smsService;

    private String to = "18382471393";
    private String name = "涂元坤";
    private String type = "高血压正常";


    @Test
    public void sendVerifySms() {
        String code = "123456";
        smsService.sendVerifySms(to, code);
    }

    @Test
    public void sendHealthySms() {
        smsService.sendHealthySms(to, name, type);
    }

    @Test
    public void sendHealthySms1() {
        List<String> toList = new ArrayList<>();
        toList.add("18382471393");
        toList.add("18428305872");
        smsService.sendHealthySms(toList, name, type);
    }
}