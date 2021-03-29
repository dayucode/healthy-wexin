package com.cdutcm.healthy.handler;

import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyAuthorizeException;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 16:57 星期六
 * @Description :
 * HealthyExceptionHandler
 */
@ControllerAdvice
public class HealthyExceptionHandler {

    @Autowired
    private HealthyProperties healthyProperties;

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(HealthyAuthorizeException.class)
    public ModelAndView handlerHealthyAuthorizeException(HealthyAuthorizeException e) {
        if (e.getCode().equals(ResultEnum.AUTH_ADMIN_ERROR.getCode())) {//管理员未登录
            return new ModelAndView("redirect:"
                    .concat(healthyProperties.getUrl().getAdminLogin()));
        } else if (e.getCode().equals(ResultEnum.AUTH_ADMIN_ERROR.getCode())) {//用户未登录
            return new ModelAndView("redirect:"
                    .concat(healthyProperties.getUrl().getWechatMpAuthorize())
                    .concat("/wechat/authorize"));
        }
        throw new HealthyException(ResultEnum.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(HealthyException.class)
    public ResultVO handlerHealthyException(HealthyException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception.class)
    public ResultVO handlerRegisterException(Exception e) {
        return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), e.getMessage());
    }
}