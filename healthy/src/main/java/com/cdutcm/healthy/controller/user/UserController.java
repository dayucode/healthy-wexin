package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.UserBindForm;
import com.cdutcm.healthy.dataobject.form.user.UserForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.UserVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.UserService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 15:29 星期一
 * @Description :
 */
@Api(tags = "【用户】")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserService userService;

    @ApiOperation("【用户信息】获取微信昵称和头像")
    @GetMapping("/userinfo")
    @JsonView(UserVO.UserInfoView.class)
    public ResultVO userinfo(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        //获取微信用户信息
        return ResultVOUtil.success(userService.getWxUserInfo(openid));
    }

    @ApiOperation("【用户信息】获取用户身体基本信息")
    @GetMapping("/bodyinfo")
    @JsonView(UserVO.BodyInfoView.class)
    public ResultVO bodyinfo(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        //获取身体基本信息
        return ResultVOUtil.success(userService.getUserInfo(openid));
    }

    @ApiOperation("【用户信息】完善用户身体基本信息")
    @PutMapping("/bodyinfo")
    public ResultVO perfect(@Valid @RequestBody UserForm userForm, BindingResult bindingResult,
                            @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【完善基本信息】参数错误，userForm = {}", userForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        userService.perfectUser(userForm, openid);
        return ResultVOUtil.success();
    }

    @ApiOperation("【用户信息】获取用户信息（电话、邮箱）")
    @GetMapping("/bindinfo")
    @JsonView(UserVO.BindInfoView.class)
    public ResultVO bindinfo(@ApiParam("登录凭证token") @CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        //获取身体基本信息
        return ResultVOUtil.success(userService.getUserInfo(openid));
    }

    @ApiOperation("【用户信息】绑定用户信息（电话、邮箱）")
    @PutMapping("/bindinfo")
    public ResultVO bind(@Valid @RequestBody UserBindForm userBindForm, BindingResult bindingResult,
                         @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【完善基本信息】参数错误，userBindForm = {}", userBindForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        userService.bindUser(userBindForm, openid);
        return ResultVOUtil.success();
    }
}
