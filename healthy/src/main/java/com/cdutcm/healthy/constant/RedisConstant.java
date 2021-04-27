package com.cdutcm.healthy.constant;

/**
 * @Author : daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/23 15:50 星期六
 * @Description :
 */
public interface RedisConstant {
    String TOKEN_USER = "token_user:%s";
    String TOKEN_ADMIN = "token_admin:%s";
    Integer EXPIRE = 7200;//7200
    String USER_LOCATION = "user_location:%s";
}
