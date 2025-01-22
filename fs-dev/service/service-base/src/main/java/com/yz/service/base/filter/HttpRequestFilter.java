package com.yz.service.base.filter;

import cn.hutool.json.JSONObject;
import com.yz.common.constant.CacheKey;
import com.yz.common.constant.FieldConstants;
import com.yz.common.constant.Strings;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.RedisOperator;
import com.yz.common.util.StrUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤器
 */
@Component
public class HttpRequestFilter extends OncePerRequestFilter {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private FilterExcludeUrlProperties filterExcludeUrlProperties;

    // 路径匹配规则器
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 请求体参数
    private String body;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain chain)
            throws ServletException, IOException {

        // 1. 获取当前请求URI
        String requestURI = httpServletRequest.getRequestURI();
        // System.out.println(requestURI);

        // 2. 根据自定义配置判断，是否执行过滤 true过滤，false放行
        boolean filterFlag = filterPath(requestURI);
        if (filterFlag) {
            // 缓存请求体
            BufferedReader bufferedReader = httpServletRequest.getReader();

            // 3.获取原始请求body参数
            body = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            if (StrUtils.isBlank(body)) {
                body = "{}";
            }

            // 4.解析请求体内容为JSON对象
            JSONObject bodyJson = JSONUtils.parseObj(body);
            //System.out.println(bodyJson);

            // 5.获取请求头语言编码
            String language = httpServletRequest.getHeader(FieldConstants.LANGUAGE);

            // 6.重置请求体 language 参数
            //请求头有设置语言编码，则优先使用
            if (StrUtils.isNotBlank(language)) {
                bodyJson.put(FieldConstants.LANGUAGE,language);
                // 重置后请求body参数
                body = JSONUtils.toJsonStr(bodyJson);
            }
            String finalLanguage = (String)bodyJson.get(FieldConstants.LANGUAGE);
            if (StrUtils.isBlank(finalLanguage)) {
                // 请求头和请求体都没有 language 参数，则默认西语
                finalLanguage = Strings.LOCALE_ES_LOWER;
                bodyJson.put(FieldConstants.LANGUAGE,finalLanguage);
                // 重置后请求body参数
                body = JSONUtils.toJsonStr(bodyJson);
            }

            // 7. 包装 HttpServletRequestWrapper 对象，以便后续的处理
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

            // 8. 包装 自定义的 HttpResponseWrapper 对象，以便后续的处理
            HttpResponseWrapper responseWrapper = new HttpResponseWrapper(httpServletResponse);

            // 9. 放行，调用下一个过滤器或Servlet
            chain.doFilter(requestWrapper, responseWrapper);

            // 10.获取响应数据
            String responseData = responseWrapper.getResponseData(StandardCharsets.UTF_8.name());
            //System.out.println(responseData);

            // 11. 解析响应数据为JSON对象
            JSONObject responseJson = JSONUtils.parseObj(responseData);

            // 12. 对响应数据进行处理
            // 根据语言编码赋值对应的值
            String code = (String)responseJson.get("code");
            language = (String)bodyJson.get(FieldConstants.LANGUAGE);
            String key = CacheKey.MIS + CacheKey.T_RESPONSE_ERROR_ENUMS + code + Strings.COLON + language;
            String msg = redisOperator.get(key);
            if (ObjectUtils.isNotNull(msg)) {
                responseJson.put("msg",msg);
            } else {
                // 缓存没有查询数据库
                responseJson.put("msg","Not configured");
            }
            // 13. 将修改后的 JSON 对象转换为字符串
            responseData = JSONUtils.toJsonStr(responseJson);

            // 14. 将修改后的 JSON 对象设置为最终的响应数据
            responseWrapper.setResponseData(responseData, StandardCharsets.UTF_8.name());

            // 15. 将响应数据写入原始的响应对象，解决响应数据无法被多个过滤器处理问题
            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(responseData.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } else {
            // 16. 非过滤路径的请求，直接放行
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    // 判断当前请求是否执行过滤，true是，false否（直接放行）
    private boolean filterPath(String requestURI) {
        boolean filterFlag = false;
        if (filterExcludeUrlProperties != null) {
            // 1. 获得所有需要进行过滤的url
            List<String> urls = filterExcludeUrlProperties.getUrls();
            // 2. 获得所有需要排除过滤的url
            List<String> excludeUrls = filterExcludeUrlProperties.getExcludeUrls();

            // 3. 匹配过滤的url
            if (urls != null && !urls.isEmpty()) {
                for (String url : urls) {
                    if (antPathMatcher.match(url, requestURI)) {
                        filterFlag = true;
                        break;
                    }
                }
            }
            // 4. 匹配排除过滤的url
            if (filterFlag && excludeUrls != null && !excludeUrls.isEmpty()) {
                for (String excludeUrl : excludeUrls) {
                    if (antPathMatcher.match(excludeUrl, requestURI)) {
                        filterFlag = false;
                        break;
                    }
                }
            }
        }
        return filterFlag;
    }

}
