package com.yz.service.base.filter;

import cn.hutool.json.JSONObject;
import com.yz.common.constant.FieldConstants;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.StrUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

@WebFilter(urlPatterns = "/*")
public class HttpRequestFilter implements Filter {

    // 请求体参数
    private String body;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 缓存请求体
        BufferedReader bufferedReader = httpServletRequest.getReader();
        // 原始请求body参数
        body = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));

        //获取请求头语言编码
        String language = httpServletRequest.getHeader(FieldConstants.LANGUAGE);

        //请求头有设置语言编码，则优先使用
        if (StrUtils.isNotBlank(language)) {
            // 获取请求body参数
            JSONObject bodyJson = JSONUtils.parseObj(body);
            bodyJson.put(FieldConstants.LANGUAGE,language);
            // 重置后请求body参数
            body = JSONUtils.toJsonStr(bodyJson);
        }

        // 重设请求body参数
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest) {
            @Override
            public BufferedReader getReader() {
                return new BufferedReader(new StringReader(body));
            }

            @Override
            public ServletInputStream getInputStream() {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
                return new ServletInputStream() {
                    @Override
                    public boolean isFinished() {
                        return false;
                    }
                    @Override
                    public boolean isReady() {
                        return false;
                    }
                    @Override
                    public void setReadListener(ReadListener readListener) {
                    }

                    @Override
                    public int read() {
                        return byteArrayInputStream.read();
                    }
                };
            }
        };

        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }

}
