package com.example.demo.utils;

import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * @description: 指标、reporter、registry初始化类,整个metric模块的入口
 * @author: 邹凌峰
 * @create: 2018-08-06
 **/
public class Metric {

    private static final Logger logger = LoggerFactory.getLogger(Metric.class);

    private static final MetricRegistry registry = new MetricRegistry();
    public static final CustomedGauge sqlGauge = new CustomedGauge();
    public static final CustomedGauge redisGauge = new CustomedGauge();
    public static final CustomedGauge httpGauge = new CustomedGauge();
    public static final WebGauge webGauge = new WebGauge();
    private String project;
    private String host = "";

    public Metric(ProjectEnum projectEnum, InfluxDBHostEnum hostEnum) {
        this.project = projectEnum.getProject();
        try {
            String localIp = InetAddress.getLocalHost().getHostAddress();
            if (projectEnum.getIps().contains(localIp)) {
                this.host = hostEnum.getHost();
                registry.register("sql", sqlGauge);
                registry.register("redis", redisGauge);
                registry.register("web", webGauge);
                registry.register("http", httpGauge);
                this.initMetricsReporter();
            }
        } catch (Exception e) {
            logger.error("get local ip error!! -> " + e.getMessage());
        }
    }

    private void initMetricsReporter() {
        MetricsReporter.forRegistry(registry)
                .protocol(InfluxdbProtocols.http(this.host, 8086, "admin", "admin", "bytedance"))
                .project(this.project)
                .build();
    }
}