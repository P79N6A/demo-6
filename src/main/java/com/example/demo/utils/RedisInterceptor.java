package com.example.demo.utils;


/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public class RedisInterceptor implements Interceptor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public void before(Object target) {
        System.out.println("before============");
        long start = System.currentTimeMillis();
        startTime.set(Long.valueOf(start));
    }

    @Override
    public void after(Object target) {
        System.out.println("after============");
        long start = startTime.get().longValue();
        Metric.redisGauge.add(1, System.currentTimeMillis() - start);
    }

    @Override
    public void processException(Object target, Exception e) {
        System.out.println("processException============");
        long start = startTime.get().longValue();
        Metric.redisGauge.add(0, System.currentTimeMillis() - start);
    }
}