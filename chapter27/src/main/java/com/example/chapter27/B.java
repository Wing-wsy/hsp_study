package com.example.chapter27;

/**
 * @author wing
 * @create 2024/6/30
 */
public class B implements SecurityBuilder<O> {

    @Override
    public O build() throws Exception {
        return new O();
    }
}
