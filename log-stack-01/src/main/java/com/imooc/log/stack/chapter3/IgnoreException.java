package com.imooc.log.stack.chapter3;

import lombok.Data;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * <h1>忽略异常</h1>
 * */
public class IgnoreException {

    @Data
    public static class Imoocer {
        private String name;
        private String gender;
    }

    // 1. for 循环中大批量的处理数据, 一般都不会让异常直接抛出
    public void batchProcess(List<Imoocer> imoocers) {

        int num = 0;
        for (Imoocer imoocer : imoocers) {
            try {
                num += (imoocer.getGender().equals("m") ? 0 : 1);
            } catch (Exception ex) {
                // 记录下异常情况
            }
        }

        System.out.println("female imoocer num is: " + num);
    }

    // 2. 存在网络请求(RPC), 允许一定次数的失败重试, 即忽略掉偶发性的异常
    private static void sendGet() throws Exception {

        URL obj = new URL("www.imooc.com");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }

    // 3. 不影响业务的整体逻辑情况, 例如手机验证码发送失败
}
