package com.example.demo.utils;

import com.codahale.metrics.Gauge;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-08
 **/
public class CustomedGauge implements Gauge {

    public final AtomicLong successCount = new AtomicLong(0);
    public final AtomicLong count = new AtomicLong(0);         //访问次数
    public final AtomicLong delayTime = new AtomicLong(0);          //每次采集中的总耗时长

    public synchronized void add(long delta, long time) {
        successCount.getAndAdd(delta);
        count.incrementAndGet();
        delayTime.getAndAdd(time);
    }

    public synchronized void reset() {
        successCount.set(0);
        count.set(0);
        delayTime.set(0);
    }

    //获取错误率
    public double getErrorRatio() {
        double errorCount = count.doubleValue() - successCount.doubleValue();
        if (count.get() == 0) return 0;
        return errorCount / count.get();
    }

    //获取qps
    public Long getCount() {
        return Long.valueOf(count.longValue());
    }

    //获取单次访问的平均响应时间
    public double getResponseTime() {
        if (count.get() == 0) return 0;
        return delayTime.doubleValue() / count.doubleValue();
    }

    @Override
    public Object getValue() {
        return null;
    }
}