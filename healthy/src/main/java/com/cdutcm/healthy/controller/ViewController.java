package com.cdutcm.healthy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/15 19:50 星期五
 * @Description :
 */
@Api(tags = "【DEMO】")
@Controller
@RequestMapping("/")
public class ViewController {

    @ApiOperation("【微信JS-SDK】Demo，利用微信客户端打开链接")
    @GetMapping("/jssdk")
    public String jssdk() {
        return "jssdk";
    }

    @ApiOperation("【wangEditor】Demo，wangEditor实例。")
    @GetMapping("/wange")
    public String wange() {
        return "wange";
    }

    @ApiOperation("【call】JS调起拨号功能")
    @GetMapping("/call")
    public String call() {
        return "call";
    }
}
