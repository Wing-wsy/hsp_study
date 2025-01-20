//package com.yz.mis.filter;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@WebFilter("/*")
//@Component
//public class RequestWrapperFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        System.out.println("doFilter===");
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("destroy===");
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("init===");
//    }
//
////    public void preHandle(CustomHttpServletRequestWrapper request) throws Exception {
////        //仅当请求方法为POST时修改请求体
////        if (!request.getMethod().equalsIgnoreCase("POST")) {
////            return;
////        }
////        //读取原始请求体
////        StringBuilder originalBody = new StringBuilder();
////        String line;
////        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
////            while ((line = reader.readLine()) != null) {
////                originalBody.append(line);
////            }
////        }
////        String bodyText = originalBody.toString();
////        //json字符串转成map集合
////        Map<String, String> map = getMap(bodyText);
////        //获取解密参数，解密数据
//////        if (map != null && map.containsKey("time") && map.containsKey("data")) {
//////            String time = map.get("time");
//////            String key = "基于时间戳等参数生成密钥、此处请换成自己的密钥";
//////            String data = map.get("data");
//////            //解密数据
//////            String decryptedData = Cipher.decrypt(key, data);
//////            //为请求对象重新设置body
//////            request.setBody(decryptedData);
//////        }
////    }
////
////    private Map<String, String> getMap(String text) {
////        return null;
////    }
//}
