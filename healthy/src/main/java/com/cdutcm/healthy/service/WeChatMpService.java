package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.vo.user.BodyIndexVO;
import com.cdutcm.healthy.dataobject.wechat.AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/14 19:08 星期四
 * @Description :
 */
public interface WeChatMpService {
    //获取AccessToken
    AccessToken getAccessToken();

    //创建自定义菜单
    boolean createMenu();

    // 被动回复分发
    String wechatMpDispatcher(WxMpXmlMessage wxMpXmlMessage);

    // 发送身体指标信息
    void sendWxMpTempMsg(BodyIndexVO bodyIndexVO) throws WxErrorException;
}
