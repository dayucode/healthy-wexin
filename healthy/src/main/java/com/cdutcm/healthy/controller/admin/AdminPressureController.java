package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureTendencyVO;
import com.cdutcm.healthy.service.PressureService;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/21 20:37 星期四
 * @Description :
 */
@Api(tags = "【管理员-血压】")
@Slf4j
@RestController
@RequestMapping("/admin/pressure")
public class AdminPressureController {
    @Autowired
    private PressureService pressureService;

    @ApiOperation("【血压记录】血压所有的历史记录")
    @GetMapping("/history")
    @JsonView(PressureTendencyVO.PressureHistoryView.class)
    public ResultVO history(@RequestParam("openid") String openid, PageForm page) {
        return ResultVOUtil.success(pressureService.historyPressure(openid, page));
    }
}