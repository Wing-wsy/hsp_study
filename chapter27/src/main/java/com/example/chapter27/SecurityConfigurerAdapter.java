package com.example.chapter27;

import org.springframework.util.Assert;

/**
 * @author wing
 * @create 2024/6/30
 */
public abstract class SecurityConfigurerAdapter<O, B extends SecurityBuilder<O>> implements SecurityConfigurer<O, B> {

    private B securityBuilder;

    @Override
    public void init(B builder) throws Exception {
    }

    @Override
    public void configure(B builder) throws Exception {
    }

    public B and() {
        return getBuilder();
    }

    protected final B getBuilder() {
        return this.securityBuilder;
    }
}
