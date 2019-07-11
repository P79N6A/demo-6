package com.example.demo.utils;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MetricsReporter {

    static final Logger logger = LoggerFactory.getLogger(MetricsReporter.class);

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Sender sender;
    private final MetricRegistry registry;
    private final String project;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private MetricsReporter(Sender sender, MetricRegistry registry, String project) {
        this.project = project;
        this.sender = sender;
        this.registry = registry;
        this.start(10, TimeUnit.SECONDS);
    }

    public void report() {
        try {
            SortedMap<String, Gauge> gaugeSortedMap = this.registry.getGauges();
            for (Map.Entry<String, Gauge> entry : gaugeSortedMap.entrySet()) {
                String metricName = entry.getKey();
                this.sender.send(this.fromGauge(metricName, entry.getValue(), System.currentTimeMillis()));
            }
            this.sender.flush();
        } catch (Exception e) {
            logger.error("metrics report to influxdb error! " + e.getMessage());
        }
    }

    private Measurement fromGauge(String metricName, Gauge g, long timestamp) throws Exception {
        Measure measure = (new Measure(metricName)).timestamp(timestamp);
        if (g instanceof CustomedGauge) {
            measure.addValue("error_rate", ((CustomedGauge) g).getErrorRatio());
            measure.addValue("qps", ((CustomedGauge) g).getCount());
            measure.addValue("response_time", ((CustomedGauge) g).getResponseTime());
            measure.addValue("project", this.project);
            measure.addValue("ip", InetAddress.getLocalHost().getHostAddress());
            if (g instanceof WebGauge) {
                measure.addValue("online_count", ((WebGauge) g).getOnlineCount());
            }
            ((CustomedGauge) g).reset();
            return measure;
        }
        Object o = g.getValue();
        if (o == null) {
            return null;
        } else if (!(o instanceof Long) && !(o instanceof Integer)) {
            if (o instanceof Double) {
                Double d = (Double) o;
                if (d.isInfinite() || d.isNaN()) {
                    measure.addValue("value", 0);
                } else {
                    measure.addValue("value", d.doubleValue());
                }
            } else {
                String value = "" + o;
                measure.addValue("value", value);
            }
        } else {
            long value = ((Number) o).longValue();
            measure.addValue("value", value);
        }
        return measure;
    }

    public static class Builder {
        private final MetricRegistry registry;
        private String project;
        InfluxdbProtocol protocol;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.protocol = InfluxdbProtocols.http();
        }

        public MetricsReporter build() {
            Sender s = new HttpInlinerSender((HttpInfluxdbProtocol) this.protocol);
            return new MetricsReporter(s, this.registry, this.project);
        }

        public Builder protocol(InfluxdbProtocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder project(String project) {
            this.project = project;
            return this;
        }
    }

    public void start(long period, TimeUnit unit) {
        this.executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                MetricsReporter.this.report();
            }
        }, period, period, unit);
    }

    public void stop() {
        this.executor.shutdown();

        try {
            this.executor.awaitTermination(1L, TimeUnit.SECONDS);
        } catch (InterruptedException var2) {
            logger.error("reporter executor stop error! " + var2.getMessage());
        }

    }
}