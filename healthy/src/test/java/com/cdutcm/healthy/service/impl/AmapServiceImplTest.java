package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.service.AmapService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AmapServiceImplTest extends HealthyApplicationTests {

    @Autowired
    private AmapService amapService;

    @Test
    public void regeo() {
        String regeo = amapService.regeo("116.29522008325625,39.99426090286774");
        System.out.println("regeo = " + regeo);
    }
}