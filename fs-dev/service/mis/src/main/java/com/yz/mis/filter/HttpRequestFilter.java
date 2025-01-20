//package com.yz.mis.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//
//import javax.servlet.annotation.WebFilter;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.stream.Collectors;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebFilter(urlPatterns = "/*")
//public class HttpRequestFilter implements Filter {
//
//    private String body;
//    @Override
//    public void init(FilterConfig filterConfig) {
//        System.out.println("init===");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("doFilter===");
//
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//
//        // 缓存请求体
//        BufferedReader bufferedReader = httpServletRequest.getReader();
//        // 获取请求body参数
//        body = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
//
//        // 重新赋值请求body参数
//                        body = "{\n" +
//                        "    \"language\":\"zh\"\n" +
//                        "}";
//
//        // 重设请求body参数
//        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest) {
//            @Override
//            public BufferedReader getReader() {
//                return new BufferedReader(new StringReader(body));
//            }
//
//            @Override
//            public ServletInputStream getInputStream() {
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
//                return new ServletInputStream() {
//                    @Override
//                    public boolean isFinished() {
//                        return false;
//                    }
//                    @Override
//                    public boolean isReady() {
//                        return false;
//                    }
//                    @Override
//                    public void setReadListener(ReadListener readListener) {
//                    }
//
//                    @Override
//                    public int read() {
//                        return byteArrayInputStream.read();
//                    }
//                };
//            }
//        };
//
//        chain.doFilter(requestWrapper, response);
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("destroy===");
//    }
//
//}
