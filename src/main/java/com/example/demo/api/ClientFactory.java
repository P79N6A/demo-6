package com.example.demo.api;

import com.example.demo.api.didi.DidiClient;
import com.example.demo.properties.DidiProperties;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class ClientFactory {

    @Bean
    @Lazy
    public DidiClient getDidiClient(DidiProperties didiProperties) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logLevel(Logger.Level.FULL)
                .target(DidiClient.class, didiProperties.getHost());
    }
}
