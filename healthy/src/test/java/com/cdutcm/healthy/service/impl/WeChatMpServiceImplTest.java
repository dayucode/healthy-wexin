package com.cdutcm.healthy.service.impl;

import com.cdutcm.healthy.HealthyApplicationTests;
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
//        assertEquals(true, weChatMpService.createMenu());

        WxMenu wxMenu = new WxMenu();
        List<WxMenuButton> wxMenuButtonList = new ArrayList<>();

        WxMenuButton button1 = new WxMenuButton();
        button1.setName("项目主页");
        button1.setUrl("http://api.healthy.tuyrk.cn/wechat/authorize");
//        button1.setUrl("http://tyk.nat300.top/wechat/authorize");
        button1.setType(WxConsts.MenuButtonType.VIEW);
        button1.setKey("1");

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("扫码登录");
        button2.setType(WxConsts.MenuButtonType.SCANCODE_WAITMSG);
        button2.setKey("2");

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