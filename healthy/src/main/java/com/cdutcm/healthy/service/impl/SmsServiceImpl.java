package com.cdutcm.healthy.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.service.SmsService;
import com.cdutcm.healthy.utils.GsonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/25 15:23 星期一
 * @Description :
 */
@Service
public class SmsServiceImpl implements SmsService {

    private String accessKeyId;
    private String secret;
    private String signName;
    private String healthyTemplateCode;
    private String verifyTemplateCode;


    public SmsServiceImpl(HealthyProperties healthyProperties) {
        accessKeyId = healthyProperties.getAliyun().getAccessKeyId();
        secret = healthyProperties.getAliyun().getSecret();
        signName = healthyProperties.getAliyun().getSignName();
        healthyTemplateCode = healthyProperties.getAliyun().getHealthyTemplateCode();
        verifyTemplateCode = healthyProperties.getAliyun().getVerifyTemplateCode();
    }

    // 发送验证码
    @Override
    public void sendVerifySms(String to, String code) {
        sendSms(verifyTemplateCode, to, toVerifyMsg(code));
    }

    // 发送健康通知
    @Override
    public void sendHealthySms(String to, String name, String type) {
        sendSms(healthyTemplateCode, to, toHealthyMsg(name, type));
    }

    @Override
    public void sendHealthySms(List<String> toList, String name, String type) {
        String healthyMsg = toHealthyMsg(name, type);
        for (String to : toList) {
            sendSms(healthyTemplateCode, to, healthyMsg);
        }
    }


    private void sendSms(String templateCode, String to, String msg) {
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", to);// 接收手机号
        request.putQueryParameter("SignName", signName);// 短信签名
        request.putQueryParameter("TemplateCode", templateCode);// 短信模板CODE
        request.putQueryParameter("TemplateParam", msg);// 模板变量
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼装健康状态的JSON字符串
     */
    private String toHealthyMsg(String name, String type) {
        Map<String, String> msg = new HashMap<>();
        msg.put("name", name);
        msg.put("type", type);
        return GsonUtil.toString(msg);
    }

    /**
     * 拼装验证码的JSON字符串
     */
    private String toVerifyMsg(String code) {
        Map<String, String> msg = new HashMap<>();
        msg.put("code", code);
        return GsonUtil.toString(msg);
    }
}
