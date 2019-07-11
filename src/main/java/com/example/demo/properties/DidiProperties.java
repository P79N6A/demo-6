package com.example.demo.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "didi")
public class DidiProperties {

    private String clientId;

    private String signKey;

    private String clientSecret;

    private String grantType;

    private String phone;

    private String companyId;

    private String host;
}
