package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.ObesityForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityTendencyVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.ObesityService;
import com.cdutcm.healthy.service.RedisOperator;
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
@Api(tags = "【用户-体重】")
@Slf4j
@RestController
@RequestMapping("/user/obesity")
public class ObesityController {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private ObesityService obesityService;

    @ApiOperation("【记录体重】记录用户体重")
    @PostMapping("/record")
    public ResultVO record(@Valid @RequestBody ObesityForm obesityForm, BindingResult bindingResult,
                           @ApiParam("登录凭证token")@CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【记录体重】参数错误，obesityForm = {}", obesityForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        obesityService.recordObesity(obesityForm, openid);
        return ResultVOUtil.success();
    }

    @ApiOperation("【测量体重】利用体重信息计算BMI等级")
    @GetMapping("/measure")
    public ResultVO measure(@ApiParam("登录凭证token")@CookieValue("token") String token,
                            @ApiParam("记录体重值")@RequestParam(value = "obesityValue") Double obesityValue) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(obesityService.measureObesity(obesityValue, openid));
    }

    @ApiOperation("【最近体重】最近一次测量的体重信息")
    @GetMapping("/newly")
    public ResultVO newly(@ApiParam("登录凭证token")@CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(obesityService.newlyObesity(openid));
    }

    @ApiOperation("【体重变化趋势】近三个月体重统计信息")
    @GetMapping("/census")
    @JsonView(ObesityTendencyVO.ObesityCensusView.class)
    public ResultVO census(@ApiParam("登录凭证token")@CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(obesityService.censusObesity(openid));
    }

    @ApiOperation("【体重记录】体重所有的历史记录")
    @GetMapping("/history")
    @JsonView(ObesityTendencyVO.ObesityHistoryView.class)
    public ResultVO history(@ApiParam("登录凭证token")@CookieValue("token") String token, PageForm page) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(obesityService.historyObesity(openid, page));
    }
}