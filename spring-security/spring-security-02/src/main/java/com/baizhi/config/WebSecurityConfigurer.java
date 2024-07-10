package com.baizhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 *  继承 WebSecurityConfigurerAdapter 并覆写configure(HttpSecurity http) 方法可以达到自定义配置
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    private final MyUserDetailService myUserDetailService;

    @Autowired
    public WebSecurityConfigurer(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        // 重置登录用户名和密码分别为：aaa、123
        userDetailsService.createUser(User.withUsername("aaa").password("{noop}123").roles("admin").build());
        return userDetailsService;
    }*/

    //springboot 对 security 默认配置中  在工厂中默认创建 AuthenticationManager 【方式一】
    /*@Autowired
    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("springboot 默认配置: " + builder);
    }*/

    //自定义全局AuthenticationManager  推荐  并没有在工厂中暴露出来 【方式二】
    //自定义就没有默认配置干扰，看起来比较清楚了
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("自定义AuthenticationManager: " + builder);
//        builder.userDetailsService(userDetailsService());
        builder.userDetailsService(myUserDetailService);
        // 这里还可以自定义 Provider
//        builder.authenticationProvider(new AuthenticationProvider() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//
//            @Override
//            public boolean supports(Class<?> authentication) {
//                return false;
//            }
//        });
    }

    //作用: 用来将自定义AuthenticationManager在工厂中进行暴露,可以在任何位置注入（比如在HelloController中注入使用）
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 覆写configure(HttpSecurity http) 方法可以达到自定义配置，不再走默认
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login.html").permitAll() // 放行访问LoginController的/login.html这个请求（注意放行的是请求，而不是页面）
                .mvcMatchers("/index").permitAll() //放行资源写在.anyRequest().authenticated()前面
                .anyRequest().authenticated()  // 除了上面配置的/index之外全部需要认证才能访问
                .and()
                .formLogin()
                .loginPage("/login.html") //用来指定默认登录页面 注意: 一旦自定义登录页面以后必须指定登录url .loginProcessingUrl("/doLogin")
                .loginProcessingUrl("/doLogin")  //指定处理登录请求 url
                .usernameParameter("uname") // 更改接受参数名称为 uname，不再使用默认 username
                .passwordParameter("passwd") // 更改接受参数名称为 passwd，不再使用默认 password
                //.successForwardUrl("/index") //认证成功 forward 跳转路径  始终在认证成功之后跳转到指定请求【前后端不分离方案】
                //.defaultSuccessUrl("/index", true) //认证成功 redirect 之后跳转   根据上一保存请求进行成功跳转【前后端不分离方案】
                .successHandler(new MyAuthenticationSuccessHandler()) //认证成功时处理 【前后端分离解决方案】
                //.failureForwardUrl("/login.html") //认证失败之后 forward 跳转
                //.failureUrl("/login.html") // 默认 认证失败之后 redirect 跳转
                .failureHandler(new MyAuthenticationFailureHandler()) //用来自定义认证失败之后处理  【前后端分离解决方案】
                .and()
                .logout()
                //.logoutUrl("/logout")  //指定注销登录 url 默认请求方式必须: GET（可以自定义为POST，看下面）
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/aa", "GET"), // 扩展注销url
                        new AntPathRequestMatcher("/bb", "POST") // 扩展注销url并使用POST请求
                ))
                .invalidateHttpSession(true) // 会话失效
                .clearAuthentication(true)   // 清除认证标记
                //.logoutSuccessUrl("/login.html") //注销登录 成功之后跳转页面
                .logoutSuccessHandler(new MyLogoutSuccessHandler())  //注销登录成功之后处理 【前后端分离解决方案】
                .and()
                .csrf().disable()//禁止 csrf 跨站请求保护

        ;
    }
}
