package com.baizhi.config;

import com.baizhi.service.MyUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // 使用自定义数据库来验证
    private final MyUserDetailService myUserDetailService;

    @Autowired
    public SecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义数据库来验证
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //自定义 filter 交给工厂管理
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin");//指定认证 url
        loginFilter.setUsernameParameter("uname");//指定接收json 用户名 key
        loginFilter.setPasswordParameter("passwd");//指定接收 json 密码 key
        // LoginFilter过滤器中使用到 AuthenticationManager ，这里需要注入
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录成功");
            result.put("用户信息", authentication.getPrincipal());
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(HttpStatus.OK.value());
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req, resp, ex) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录失败: " + ex.getMessage());
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest().authenticated()//所有请求必须认证
                .and()
                .formLogin()
                // 没有通过认证后端不响应登录界面，而是返回json提示
                .and()
                // 默认没有认证去访问资源时，后端会返回登录页面，而前后端分离，没有认证后端应该返回json提示未认证，所以可以使用下面认证异常处理实现
                // 获取异常处理实例
                .exceptionHandling()
                // 认证异常处理
                .authenticationEntryPoint((req, resp, ex) -> {
                    resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resp.getWriter().println("请认证之后再去处理!");
                })
                .and()
                .logout()
                .logoutRequestMatcher(new OrRequestMatcher(
                        // 也支持下面两种注销方式
                        new AntPathRequestMatcher("/logout", HttpMethod.DELETE.name()),
                        new AntPathRequestMatcher("/logout", HttpMethod.GET.name())
                ))
                .logoutSuccessHandler((req, resp, auth) -> {
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("msg", "注销成功");
                    result.put("用户信息", auth.getPrincipal());
                    resp.setContentType("application/json;charset=UTF-8");
                    resp.setStatus(HttpStatus.OK.value());
                    String s = new ObjectMapper().writeValueAsString(result);
                    resp.getWriter().println(s);
                })
                .and()
                .csrf().disable()
        ;
        /*
           at: 用来某个 filter 替换过滤器链中哪个 filter
           before: 放在过滤器链中哪个 filter 之前
           after: 放在过滤器链中那个 filter 之后
         */
        // 这里用的是 at
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
