package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.dataobject.vo.user.SugarVO;
import com.cdutcm.healthy.service.SugarService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SugarServiceImplTest extends HealthyApplicationTests {

    private static final String openid = "oYnw56OEcTV8oekci1lk-ss-YvoQ";

    @Autowired
    private SugarService sugarService;

    @Test
    public void recordSugar() {
    }

    @Test
    public void recordSugar1() {
    }

    @Test
    public void measureSugar() {
    }

    @Test
    public void newlySugar() {
        SugarVO sugarVO = sugarService.newlySugar(openid);
    }

    @Test
    public void censusSugar() {
    }

    @Test
    public void historySugar() {
    }
}