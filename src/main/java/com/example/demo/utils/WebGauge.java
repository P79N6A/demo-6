package com.example.demo.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-09
 **/
public class WebGauge extends CustomedGauge {

    private final AtomicLong onlineCount = new AtomicLong(0);          //访问人数

    public synchronized void add(long delta) {
        successCount.getAndAdd(delta);
        count.incrementAndGet();
    }

    public synchronized void addTime(long delay) {
        delayTime.getAndAdd(delay);
    }

    public Long getOnlineCount() {
        return onlineCount.longValue();
    }
}