package com.example.demo.utils;


/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public interface Interceptor {

    void before(Object target);

    void after(Object target);

    void processException(Object target, Exception e);
}