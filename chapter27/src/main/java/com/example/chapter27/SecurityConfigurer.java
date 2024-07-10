package com.example.chapter27;

/**
 * @author wing
 * @create 2024/6/30
 */
public interface SecurityConfigurer <O, B extends SecurityBuilder<O>> {

    void init(B builder) throws Exception;

    void configure(B builder) throws Exception;

}
