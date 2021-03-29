package com.cdutcm.healthy.controller.open;

import com.cdutcm.healthy.dataobject.mapper.OpenApiMapper;
import com.cdutcm.healthy.dataobject.open.OpenApiParam;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/10 22:11 星期三
 * @Description :
 */
@Api(tags = "【开放平台】")
@Slf4j
@RestController
@RequestMapping("/open")
public class OpenController {
    @Autowired
    private OpenApiMapper openApiMapper;

    @ApiOperation("【血压记录】血压所有的历史记录")
    @GetMapping("/pressure")
    public ResultVO pressure(OpenApiParam openApiParam) {
        return ResultVOUtil.success(openApiMapper.selectPressure(openApiParam));
    }

    @ApiOperation("【血糖记录】血糖所有的历史记录")
    @GetMapping("/sugar")
    public ResultVO sugar(OpenApiParam openApiParam) {
        return ResultVOUtil.success(openApiMapper.selectSugar(openApiParam));
    }

    @ApiOperation("【体重记录】体重所有的历史记录")
    @GetMapping("/obesity")
    public ResultVO obesity(OpenApiParam openApiParam) {
        return ResultVOUtil.success(openApiMapper.selectObesity(openApiParam));
    }

    @ApiOperation("【冠心病评估记录】冠心病评估所有的历史记录")
    @GetMapping("/coronary")
    public ResultVO coronary(OpenApiParam openApiParam) {
        return ResultVOUtil.success(openApiMapper.selectCoronary(openApiParam));
    }
}
