package com.cdutcm.healthy.constant;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/15 16:50 星期五
 * @Description :
 */
public interface WxMpReturnMsg {

    String RECORD_SUCCESS = "记录成功";
    String RECORD_ERROR = "记录失败";
    String IDENT_INFO_ERROR = "未能识别信息";
    String HIGH_LOW_PRESSURE_NOT_EXISTS = "高压与低压必须同时输入";
    String PHONE_ERROR = "手机号码不规范";
    String PHONE_EXISTS = "手机号码已存在";

    String DELETE_SUCCESS = "删除成功";
    String DELETE_ERROR = "删除失败";

    String FAMILY_NOT_SUBSCRIBE = "家属暂未关注公众号或未绑定手机号";
    String FAMILY_EXISTS = "家属已绑定";
    String LOGIN_SUCCESS = "登录成功";
    String LOGIN_ERROR = "登录失败";
    String AUTH_ERROR = "权限不足";
}
