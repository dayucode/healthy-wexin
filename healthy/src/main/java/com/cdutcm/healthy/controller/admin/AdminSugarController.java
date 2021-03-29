package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarTendencyVO;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.SugarService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 18:57 星期日
 * @Description :
 */
@Api(tags = "【管理员-血糖】")
@Slf4j
@RestController
@RequestMapping("/admin/sugar")
public class AdminSugarController {
    @Autowired
    private SugarService sugarService;

    @ApiOperation("【血糖记录】血糖所有的历史记录")
    @GetMapping("/history")
    @JsonView(SugarTendencyVO.SugarHistoryView.class)
    public ResultVO history(@RequestParam("openid") String openid, PageForm page) {
        return ResultVOUtil.success(sugarService.historySugar(openid, page));
    }
}