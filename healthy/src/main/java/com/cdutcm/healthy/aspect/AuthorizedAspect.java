package com.cdutcm.healthy.aspect;

import com.cdutcm.healthy.constant.CookieConstant;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyAuthorizeException;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.utils.Base64Util;
import com.cdutcm.healthy.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/3/26 15:35 ζζδΊ
 * @Description :
 */
@Slf4j
@Aspect
@Component
public class AuthorizedAspect {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserDao userDao;

    @Pointcut("execution(public * com.cdutcm.healthy.controller.user.*.*(..))" +
            "&&!execution(public * com.cdutcm.healthy.controller.user.WechatController.*(..))")
    public void verifyUser() {
    }

    @Pointcut("execution(public * com.cdutcm.healthy.controller.admin.*.*(..))" +
            "&&!execution(public * com.cdutcm.healthy.controller.admin.AdminController.login(..))" +
            "&&!execution(public * com.cdutcm.healthy.controller.admin.ScanController.*(..))")
    public void verifyAdmin() {
    }

    @Pointcut("execution(public * com.cdutcm.healthy.controller.open.*.*(..))")
    public void verifyOpen() {
    }

    @Before("verifyUser()")
    public void doVerifyUser() {
        //θ·εHttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //ζ₯θ―’Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("γη»ε½ζ ‘ιͺγCookieδΈ­ζ₯δΈε°token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_USER_ERROR);
        }

        //ε»Redisιζ₯θ―’
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_USER, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("γη»ε½ζ ‘ιͺγRedisδΈ­ζ₯δΈε°token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_USER_ERROR);
        }

    }

    @Before("verifyAdmin()")
    public void doVerifyAdmin() {
        //θ·εHttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //ζ₯θ―’Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("γη»ε½ζ ‘ιͺγCookieδΈ­ζ₯δΈε°token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }

        //ε»Redisιζ₯θ―’
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_ADMIN, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("γη»ε½ζ ‘ιͺγRedisδΈ­ζ₯δΈε°token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }
    }

    @Before("verifyOpen()")
    public void doVerifyOpen(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();//θ·εθ―·ζ±εζ°ε―Ήθ±‘ι
        try {
            for (Object arg : args) {
                // δ»θ―·ζ±εζ°δΈ­θ·εkey
                BeanMap beanMap = new BeanMap(arg);
                String key = (String) beanMap.get("key");
                if (key == null) {
                    continue;
                }
                // ε°keyθ§£ε―οΌθ·εΎopenid
                String openid = Base64Util.decode(key);
                // ε¨userθ‘¨δΈ­ζ₯ζΎopenidζ―ε¦ε­ε¨
                User user = userDao.selectByOpenid(openid);
                if (user != null) {// ε¦ζopenidε­ε¨εζζιθΏγ
                    return;
                }
            }
            throw new Exception();//θ―·ζ±δΈ­ζ²‘ζζΊεΈ¦εζ°key
        } catch (Exception e) {
            log.warn("γεΌζΎζ₯ε£γOPEN_KEYιθ――");
            throw new HealthyAuthorizeException(ResultEnum.OPEN_KEY_ERROR);
        }
    }
}