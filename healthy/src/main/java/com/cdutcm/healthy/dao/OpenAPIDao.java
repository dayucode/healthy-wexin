package com.cdutcm.healthy.dao;

import com.cdutcm.healthy.dataobject.open.OpenApiParam;
import com.cdutcm.healthy.dataobject.open.PressureVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/16 15:15 星期二
 * @Description :
 */
@Mapper
public interface OpenAPIDao {
    @Select("")
    List<PressureVO> selectPressure(OpenApiParam openApiParam);
}
