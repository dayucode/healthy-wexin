package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.HealthyApplicationTests;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.constant.WxMapMsgCategory;
import com.cdutcm.healthy.service.WeChatMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cdutcm.healthy.constant.WxMapMsgCategory.LIST_FAMILY;
import static org.junit.Assert.assertEquals;

public class WeChatMpServiceImplTest extends HealthyApplicationTests {

    private static final String openid = "oYnw56OEcTV8oekci1lk-ss-YvoQ";

    @Autowired
    private WeChatMpService weChatMpService;
    @Autowired
    private WxMpService wxMpService;

    @Test
    public void createMenu() throws WxErrorException {
        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> wxMenuButtonList = new ArrayList<>();

        WxMenuButton button1 = new WxMenuButton();
        button1.setName("我的主页");
        button1.setUrl("http://dayucode.free.idcfengye.com/wechat/authorize");
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setKey("1");

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("便捷录入");
        button2.setType(WxConsts.MenuButtonType.CLICK);
        button2.setKey(WxMapMsgCategory.HELPClick);

        Collections.addAll(wxMenuButtonList, button1, button2);
        wxMenu.setButtons(wxMenuButtonList);
        wxMpService.getMenuService().menuCreate(wxMenu);
    }

    @Test
    public void wechatMpDispatcher() {
//        String content = "绑定家属18382471394";
        String content = "解绑家属18382471394";
//        String content = LIST_FAMILY;
        WxMpXmlMessage wxMpXmlMessage = new WxMpXmlMessage();
        wxMpXmlMessage.setMsgType(WxConsts.XmlMsgType.TEXT);
        wxMpXmlMessage.setOpenId(openid);
        wxMpXmlMessage.setContent(content);
        String msg = weChatMpService.wechatMpDispatcher(wxMpXmlMessage);
        System.out.println("msg = " + msg);
    }

}