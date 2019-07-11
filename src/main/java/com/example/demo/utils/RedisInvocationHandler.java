package com.example.demo.utils;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public class RedisInvocationHandler implements InvocationHandler {

    private final Object target;
    private final InterceptorChain interceptorChain;

    public RedisInvocationHandler(Object target, InterceptorChain interceptorChain) {
        this.target = target;
        this.interceptorChain = interceptorChain;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = null;
        try {
            this.interceptorChain.preProcess(target);
            object = method.invoke(target, args);
            this.interceptorChain.postProcess(target);
        } catch (Exception e) {
            this.interceptorChain.processException(target, e);
            throw e;
        }
        return object;
    }
}