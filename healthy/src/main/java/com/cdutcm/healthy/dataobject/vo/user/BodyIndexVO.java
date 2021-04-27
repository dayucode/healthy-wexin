package com.cdutcm.healthy.dataobject.vo.user;

import lombok.Data;

import java.util.List;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/4/10 10:44 星期三
 * @Description :
 * 微信模板消息-身体指标
 */
@Data
public class BodyIndexVO {
    // 微信openid
    private List<String> openid;
    // 微信昵称
    private String nickname;
    // 位置信息
    private String location;
    // 风险类型
    private String type;
    // 风险级别
    private String level;
    // 立即呼叫
    private String phone;
}
