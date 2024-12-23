package com.example.hxds.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.hxds.common.exception.HxdsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component("MicroAppUtil")
public class MicroAppUtil {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    /**
     * 临时授权码兑换openId
     * @param code
     * @return
     */
    public String getOpenId(String code) {
        // TODO 测试环境不真实请求，临时注释
        /*
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");*/

        // TODO 测试环境使用假的 openId
        String openId = IdUtil.simpleUUID();
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap map = new HashMap() {{
            put("grant_type", "client_credential");
            put("appid", appId);
            put("secret", appSecret);
        }};
        String response = HttpUtil.get(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        if (json.containsKey("access_token")) {
            String accessToken = json.getStr("access_token");
            return accessToken;
        } else {
            throw new HxdsException(json.getStr("errmsg"));
        }
    }

    public String getTel(String phoneCode) {
        // TODO 测试环境不真实请求，临时注释
        /*
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        JSONObject param = new JSONObject();
        param.set("code", phoneCode);
        HttpRequest post = HttpUtil.createPost(url);
        post.body(param.toString(), "application/json");
        HttpResponse response = post.execute();
        JSONObject json = JSONUtil.parseObj(response.body());
        if (json.containsKey("phone_info")) {
            String tel = json.getJSONObject("phone_info").getStr("purePhoneNumber");
            return tel;
        } else {
            throw new HxdsException(json.getStr("errmsg"));
        }*/

        // TODO 测试环境使用假的 phoneCode
        return phoneCode;
    }
}
