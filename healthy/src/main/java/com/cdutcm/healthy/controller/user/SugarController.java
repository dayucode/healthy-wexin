package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.SugarForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarTendencyVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.SugarService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 18:57 星期日
 * @Description :
 */
@Api(tags = "【用户-血糖】")
@Slf4j
@RestController
@RequestMapping("/user/sugar")
public class SugarController {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private SugarService sugarService;

    @ApiOperation("【记录血糖】记录用户血糖")
    @PostMapping("/record")
    public ResultVO record(@Valid @RequestBody SugarForm sugarForm, BindingResult bindingResult,
                           @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【记录血糖】参数错误，sugarForm = {}", sugarForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        sugarService.recordSugar(sugarForm, openid);
        return ResultVOUtil.success();
    }

    @ApiOperation("【测量血糖】利用高糖低糖测量血糖类型")
    @GetMapping("/measure")
    public ResultVO measure(@ApiParam("血糖记录值") @RequestParam(value = "sugarValue") Double sugarValue) {
        return ResultVOUtil.success(sugarService.measureSugar(sugarValue));
    }

    @ApiOperation("【最近血糖】最近一次测量的血糖信息")
    @GetMapping("/newly")
    public ResultVO newly(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(sugarService.newlySugar(openid));
    }

    @ApiOperation("【血糖变化趋势】近三个月血糖统计信息")
    @GetMapping("/census")
    @JsonView(SugarTendencyVO.SugarCensusView.class)
    public ResultVO census(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(sugarService.censusSugar(openid));
    }

    @ApiOperation("【血糖记录】血糖所有的历史记录")
    @GetMapping("/history")
    @JsonView(SugarTendencyVO.SugarHistoryView.class)
    public ResultVO history(@ApiParam("登录凭证token") @CookieValue("token") String token, PageForm page) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(sugarService.historySugar(openid, page));
    }
}