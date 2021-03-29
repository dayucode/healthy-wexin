package com.cdutcm.healthy.dataobject.mapper;

import com.cdutcm.healthy.dataobject.open.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 17:05 星期二
 * @Description :
 */
@Mapper
@Component
public interface OpenApiMapper {
    /**
     *
     * @param openApiParam 请求参数
     * @return 查询结果
     */
    List<PressureVO> selectPressure(OpenApiParam openApiParam);
    List<SugarVO> selectSugar(OpenApiParam openApiParam);
    List<ObesityVO> selectObesity(OpenApiParam openApiParam);
    List<CoronaryVO> selectCoronary(OpenApiParam openApiParam);
}
