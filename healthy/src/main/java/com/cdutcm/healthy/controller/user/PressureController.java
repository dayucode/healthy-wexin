package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.PressureForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureTendencyVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.PressureService;
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
 * @Create : 2019/2/21 20:37 星期四
 * @Description :
 */
@Api(tags = "【用户-血压】")
@Slf4j
@RestController
@RequestMapping("/user/pressure")
public class PressureController {

    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private PressureService pressureService;

    @ApiOperation("【记录血压】记录用户血压")
    @PostMapping("/record")
    public ResultVO record(@Valid @RequestBody PressureForm pressureForm, BindingResult bindingResult,
                           @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【记录血压】参数错误，pressureForm = {}", pressureForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        pressureService.recordPressure(pressureForm, openid);
        return ResultVOUtil.success();
    }

    @ApiOperation("【测量血压】利用收缩压舒张压测量血压类型")
    @GetMapping("/measure")
    public ResultVO measure(@ApiParam("收缩压记录值") @RequestParam(value = "highPressure") Double highPressure,
                            @ApiParam("舒张压记录值") @RequestParam(value = "lowPressure") Double lowPressure) {
        return ResultVOUtil.success(pressureService.measurePressure(highPressure, lowPressure));
    }

    @ApiOperation("【最近血压】最近一次测量的血压信息")
    @GetMapping("/newly")
    public ResultVO newly(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(pressureService.newlyPressure(openid));
    }

    @ApiOperation("【血压变化趋势】近三个月血压统计信息")
    @GetMapping("/census")
    @JsonView(PressureTendencyVO.PressureCensusView.class)
    public ResultVO census(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(pressureService.censusPressure(openid));
    }

    @ApiOperation("【血压记录】血压所有的历史记录")
    @GetMapping("/history")
    @JsonView(PressureTendencyVO.PressureHistoryView.class)
    public ResultVO history(@ApiParam("登录凭证token") @CookieValue("token") String token, PageForm page) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(pressureService.historyPressure(openid, page));
    }
}