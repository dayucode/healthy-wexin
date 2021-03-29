package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.dao.AdminDao;
import com.cdutcm.healthy.dataobject.form.admin.LoginResponse;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 控制器
 */
@Controller
@RequestMapping("/scan")
public class ScanController {

    @Autowired
    private HealthyProperties healthyProperties;
    @Autowired
    private AdminDao adminDao;

    /**
     * 存储登录状态
     */
    private Map<String, LoginResponse> loginMap = new ConcurrentHashMap<>();

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 获取二维码
     */
    @GetMapping("/login/getQrCode")
    @ResponseBody
    public Map<String, String> getQrCode(HttpServletResponse response) throws Exception {
        Map<String, String> result = new HashMap<>(3);
        String token = UUID.randomUUID().toString();
        // app端登录地址
        String loginUrl = healthyProperties.getUrl().getHealthy()
                .concat("/scan/login/setOpenid/").concat(token).concat("/");
        result.put("token", token);
        result.put("loginUrl", loginUrl);
        result.put("image", createQrCode(loginUrl));

        Cookie cookie = new Cookie("token", token);
        System.out.println("healthyProperties.getUrl().getDomain() = " + healthyProperties.getUrl().getDomain());
        cookie.setDomain(healthyProperties.getUrl().getDomain());
        cookie.setPath("/management");
        response.addCookie(cookie);
        return result;
    }

    /**
     * app二维码登录地址，这里为了测试才传{openid},实际项目中openid是通过其他方式传值
     */
    @GetMapping("/login/setOpenid/{token}/{openid}")
    @ResponseBody
    public Map setOpenid(@PathVariable String token, @PathVariable String openid) {
        if (loginMap.containsKey(token)) {
            LoginResponse loginResponse = loginMap.get(token);
            // 赋值登录用户
            loginResponse.setOpenid(openid);
            // 查询数据库，判断该openid的管理员是否存在。未查找到则返回null
            if (adminDao.selectByOpenid(openid) != null) {
                // 唤醒登录等待线程
                loginResponse.getLatch().countDown();
                Map<String, String> map = new HashMap<>(2);
                map.put("token", token);
                map.put("openid", openid);
                return map;
            }
        }
        return null;
    }

    /**
     * 等待二维码扫码结果的长连接
     */
    @GetMapping("/login/getResponse/{token}")
    @ResponseBody
    public Map<String, Object> getResponse(@PathVariable String token) {
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        try {
            LoginResponse loginResponse;
            // 如果已经包含token
            if (loginMap.containsKey(token)) {
                loginResponse = loginMap.get(token);
            } else {
                loginResponse = new LoginResponse();
                loginMap.put(token, loginResponse);
            }

            // 第一次判断
            // 判断是否登录,如果已登录则写入session
            if (loginResponse.getOpenid() != null) {
                result.put("success", true);
                return result;
            }

            if (loginResponse.getLatch() == null) {
                loginResponse.setLatch(new CountDownLatch(1));
            }
            try {
                // 线程等待
                loginResponse.getLatch().await(5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 再次判断
            // 判断是否登录,如果已登录则写入session
            if (loginResponse.getOpenid() != null) {
                result.put("success", true);
                return result;
            }

            result.put("success", false);
            return result;
        } finally {
            // 移除登录请求
            if (loginMap.containsKey(token)) {
                loginMap.remove(token);
            }
        }
    }

    /**
     * 生成base64二维码
     */
    private String createQrCode(String content) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ImageIO.write(image, "JPG", out);
            return Base64.encodeBase64String(out.toByteArray());
        }
    }
}
