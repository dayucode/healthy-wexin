package com.cdutcm.healthy.dataobject.mapper;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.dataobject.open.*;
import com.cdutcm.healthy.utils.GsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OpenApiMapperTest extends HealthyApplicationTests {

    @Autowired
    private OpenApiMapper openApiMapper;

    @Test
    public void selectPressure() {
        OpenApiParam openApiParam = new OpenApiParam();
        openApiParam.setKey("oYnw56OEcTV8oekci1lk-ss-YvoQ");
        openApiParam.setCity("四川");
        List<PressureVO> pressureVOS = openApiMapper.selectPressure(openApiParam);
        System.out.println("pressureVOS = " + GsonUtil.prettyPrinting(pressureVOS));
        /*
        [
        PressureVO(datetime=Tue Apr 16 09:11:37 CST 2019, systolic=120.0, diastolic=87.0, location=四川省成都市锦江区三色路163号, sex=1, birthday=Fri Apr 12 05:48:31 CST 2019, height=169, weight=54),
        PressureVO(datetime=Tue Apr 16 09:11:39 CST 2019, systolic=120.0, diastolic=87.0, location=四川省成都市锦江区三色路163号, sex=1, birthday=Fri Apr 12 05:48:31 CST 2019, height=169, weight=54)
        ]
        [
        PressureVO(datetime=Tue Apr 16 09:11:37 CST 2019, systolic=120.0, diastolic=87.0, location=四川省成都市锦江区三色路163号, sex=1, birthday=Fri Apr 12 05:48:31 CST 2019, height=169, weight=54)
        ]
         */
    }

    @Test
    public void selectSugar() {
        OpenApiParam openApiParam = new OpenApiParam();
        List<SugarVO> sugarVOS = openApiMapper.selectSugar(openApiParam);
        System.out.println("sugarVOS = " + GsonUtil.prettyPrinting(sugarVOS));
    }

    @Test
    public void selectObesity() {
        OpenApiParam openApiParam = new OpenApiParam();
        List<ObesityVO> obesityVOS = openApiMapper.selectObesity(openApiParam);
        System.out.println("obesityVOS = " + GsonUtil.prettyPrinting(obesityVOS));
    }

    @Test
    public void selectCoronary() {
        OpenApiParam openApiParam = new OpenApiParam();
        List<CoronaryVO> coronaryVOS = openApiMapper.selectCoronary(openApiParam);
        System.out.println("coronaryVOS = " + GsonUtil.prettyPrinting(coronaryVOS));
    }
}