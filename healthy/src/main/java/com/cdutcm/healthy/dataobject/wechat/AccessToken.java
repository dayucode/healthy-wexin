package com.cdutcm.healthy.dataobject.wechat;

import lombok.Data;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/14 17:56 星期四
 * @Description :
 */
@Data
public class AccessToken {
    /**
     * 微信授权-JSON参数，access_token
     */
    private String access_token;
    /**
     * 微信授权-JSON参数，expires_in
     */
    private int expires_in;
}
