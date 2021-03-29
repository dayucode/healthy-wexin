package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.condition.UserCondition;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.UserVO;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.UserService;
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
 * @Create : 2019/2/26 22:59 星期二
 * @Description :
 */
@Api(tags = "【管理员-用户】")
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserService userService;

    @ApiOperation("【用户列表】获取用户信息列表")
    @GetMapping("/list")
    @JsonView(UserVO.UserListView.class)
    public ResultVO list(UserCondition userCondition, PageForm page) {
        return ResultVOUtil.success(userService.getUserList(userCondition, page));
    }

    @ApiOperation("【用户信息】获取微信昵称和头像")
    @GetMapping("/userinfo")
    @JsonView(UserVO.UserInfoView.class)
    public ResultVO info(@CookieValue("token") String token) {
        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_ADMIN, token));
        return ResultVOUtil.success(userService.getWxUserInfo(openid));
    }

    @ApiOperation("【用户信息】获取用户身体基本信息")
    @GetMapping("/bodyinfo")
    @JsonView(UserVO.BodyInfoView.class)
    public ResultVO bodyinfo(@RequestParam("openid") String openid) {
        return ResultVOUtil.success(userService.getUserInfo(openid));
    }

    @ApiOperation("【用户信息】绑定用户信息（电话、邮箱）")
    @GetMapping("/bindinfo")
    @JsonView(UserVO.BindInfoView.class)
    public ResultVO bindinfo(@RequestParam("openid") String openid) {
        return ResultVOUtil.success(userService.getUserInfo(openid));
    }
}
