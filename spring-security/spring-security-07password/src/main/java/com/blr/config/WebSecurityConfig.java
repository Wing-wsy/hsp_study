package com.blr.config;

import com.blr.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final MyUserDetailService myUserDetailService;

    @Autowired
    public WebSecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    // 如果不想使用默认的DelegatingPasswordEncoder，就想固定使用BCryptPasswordEncoder，那么可以加到bean中，这样就不会使用默认
    //【不推荐，太固定】使用 passwordEncoder 第二种配置方式（固定配置BCryptPasswordEncoder）
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/


    /*@Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //第一种 passwordEncoder 使用方式
        //inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{bcrypt}$2a$10$I/y5oopcCG6A1vgRQlD97uheTZzg70YT1q3JKPBdxXejBQLJmxRHG").roles("admin").build());
        //【不推荐，太固定】第二种 passwordEncoder 使用方式（没有使用默认DelegatingPasswordEncoder，而是使用固定的BCryptPasswordEncoder这一种了就不用写{bcrypt}）
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("$2a$10$.kKfTxVyEBT.OMb/VxQCU.FHRfzkrbHBUMKEgtKkuR8uBhh8JbqUm").roles("admin").build());
        return inMemoryUserDetailsManager;//{}
    }*/


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
//        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable();
    }
}
