package com.example.chapter27;

/**
 * @author wing
 * @create 2024/6/30
 */
public class test {
    public static void main(String[] args) {
        SecurityConfigurerImpl securityConfigurer = new SecurityConfigurerImpl();
        B b = new B();
        try {
            O init = securityConfigurer.init(b);
            System.out.println(init);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            securityConfigurer.configure(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
