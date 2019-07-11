package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-20
 **/
public enum InfluxDBHostEnum {

    PROD("prod", "10.92.1.29"), TEST("test", ""), PRE("pre", ""), LOCAL("dev", "127.0.0.1");

    private String env;
    private String host;

    InfluxDBHostEnum(String env, String host) {
        this.host = host;
    }

    public static String getHostByEnv(String env) {
        InfluxDBHostEnum[] enums = InfluxDBHostEnum.values();
        for (InfluxDBHostEnum hostEnum : enums) {
            if (StringUtils.equals(hostEnum.getEnv(), env)) return hostEnum.getHost();
        }
        return "";
    }

    public String getEnv() {
        return env;
    }

    public String getHost() {
        return host;
    }
}