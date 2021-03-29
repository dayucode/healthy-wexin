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
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/26 15:35 星期二
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
        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_USER_ERROR);
        }

        //去Redis里查询
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_USER, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_USER_ERROR);
        }

    }

    @Before("verifyAdmin()")
    public void doVerifyAdmin() {
        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }

        //去Redis里查询
        String tokenValue = redisOperator.get(String.format(RedisConstant.TOKEN_ADMIN, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new HealthyAuthorizeException(ResultEnum.AUTH_ADMIN_ERROR);
        }
    }

    @Before("verifyOpen()")
    public void doVerifyOpen(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();//获取请求参数对象集
        try {
            for (Object arg : args) {
                // 从请求参数中获取key
                BeanMap beanMap = new BeanMap(arg);
                String key = (String) beanMap.get("key");
                if (key == null) {
                    continue;
                }
                // 将key解密，获得openid
                String openid = Base64Util.decode(key);
                // 在user表中查找openid是否存在
                User user = userDao.selectByOpenid(openid);
                if (user != null) {// 如果openid存在则授权通过。
                    return;
                }
            }
            throw new Exception();//请求中没有携带参数key
        } catch (Exception e) {
            log.warn("【开放接口】OPEN_KEY错误");
            throw new HealthyAuthorizeException(ResultEnum.OPEN_KEY_ERROR);
        }
    }
}