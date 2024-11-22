package org.itzixi.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.itzixi.grace.result.GraceJSONResult;

/**
 * 远程调用工具
 * 学习博客（https://www.cnblogs.com/2324hh/p/17482633.html）
 */
@Slf4j
public class OkHttpUtil {

    public static GraceJSONResult get(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return JsonUtils.jsonToPojo(res, GraceJSONResult.class);
        } catch (Exception e) {
            log.error("OkHttp get failed:", e);
        }
        return null;
    }

}
