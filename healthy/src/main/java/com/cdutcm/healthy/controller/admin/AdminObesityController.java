package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityTendencyVO;
import com.cdutcm.healthy.service.ObesityService;
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
 * @Create : 2019/2/24 18:57 星期日
 * @Description :
 */
@Api(tags = "【管理员-体重】")
@Slf4j
@RestController
@RequestMapping("/admin/obesity")
public class AdminObesityController {
    @Autowired
    private ObesityService obesityService;

    @ApiOperation("【体重记录】体重所有的历史记录")
    @GetMapping("/history")
    @JsonView(ObesityTendencyVO.ObesityHistoryView.class)
    public ResultVO history(@RequestParam("openid") String openid, PageForm page) {
        return ResultVOUtil.success(obesityService.historyObesity(openid, page));
    }
}