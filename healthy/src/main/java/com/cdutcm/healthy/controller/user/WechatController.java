package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.service.UserService;
import com.cdutcm.healthy.service.WeChatMpService;
import com.cdutcm.healthy.utils.GsonUtil;
import com.cdutcm.healthy.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 18:45 星期一
 * @Description :
 */
@Api(tags = "【用户-微信】")
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatMpService weChatMpService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    private String wechatMpAuthorize;
    private String returnUrl;
    private String perfectUrl;
    private String domain;

    public WechatController(HealthyProperties healthyProperties) {
        this.wechatMpAuthorize = healthyProperties.getUrl().getWechatMpAuthorize();
        this.returnUrl = healthyProperties.getUrl().getReturnUrl();
        this.perfectUrl = healthyProperties.getUrl().getPerfectUrl();
        this.domain = healthyProperties.getUrl().getDomain();
    }

    @ApiOperation("【授权JSSDK】获取授权信息")
    @ResponseBody
    @GetMapping("/jsapi")
    public ResultVO jsApiSignature(@ApiParam("当前页面链接") @RequestParam("url") String url) throws WxErrorException {
        return ResultVOUtil.success(wxMpService.createJsapiSignature(url));
    }

    @ApiIgnore
    @GetMapping("/signature")
    public void checkSignature(@RequestParam("timestamp") String timestamp,
                               @RequestParam("nonce") String nonce,
                               @RequestParam("signature") String signature,
                               @RequestParam("echostr") String echostr,
                               HttpServletResponse response) throws IOException {
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            response.getWriter().println(echostr);
        }
        response.addCookie(new Cookie("appId", wxMpService.getWxMpConfigStorage().getAppId()));
        response.addCookie(new Cookie("nonceStr", timestamp));
        response.addCookie(new Cookie("timestamp", nonce));
        response.addCookie(new Cookie("signature", signature));
    }

    @ApiIgnore
    @PostMapping("/signature")
    public void dealSignature(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //从request获取用户发送的信息
        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        System.out.println("wxMpXmlMessage = " + GsonUtil.prettyPrinting(wxMpXmlMessage));
        //调用微信服务分发
        String result = weChatMpService.wechatMpDispatcher(wxMpXmlMessage);
        //如果结果不为空，则将结果回复给用户
        if (result != null) {
            response.getWriter().write(result);
        }
    }

    @ApiOperation("【微信授权登录】")
    @GetMapping("/authorize")
    public void authorize(HttpServletResponse response) throws IOException {
        //1. 配置
        //2. 调用方法
        String url = wechatMpAuthorize + "/wechat/login";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "");
        response.sendRedirect(redirectUrl);
    }

    @ApiIgnore
    @GetMapping("/login")
    public void login(@RequestParam("code") String code,
                      HttpServletResponse response) throws IOException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        WxMpUser wxMpUser;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");

        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new HealthyException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openid = wxMpOAuth2AccessToken.getOpenId();
        //是否是新用户，若是则添加记录
        User user = userService.isNewUser(openid);

        String token = UUID.randomUUID().toString();
        //设置Redis
        redisOperator.set(String.format(RedisConstant.TOKEN_USER, token), openid, RedisConstant.EXPIRE);

        user.setNickname(wxMpUser.getNickname());
        user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        userService.updateUser(user);

        Cookie cookie = new Cookie("token", token);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 检查信息是否完善，未完善则先去完善信息
        if (user.getHeight() == null) {//信息尚未完善,则身高为空
            response.sendRedirect(perfectUrl + "&token=" + token);
        }

        response.sendRedirect(returnUrl + "?token=" + token);
    }

    @ApiOperation("【模拟登录】测试时使用接口登录")
    @GetMapping("/test")
    @ResponseBody
    public ResultVO test(HttpServletResponse response) {
        String openid = "oYnw56OEcTV8oekci1lk-ss-YvoQ";
        String token = UUID.randomUUID().toString();
        //设置Redis
        redisOperator.set(String.format(RedisConstant.TOKEN_USER, token), openid, RedisConstant.EXPIRE);
        Cookie cookie = new Cookie("token", token);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultVOUtil.success(token);
    }
}