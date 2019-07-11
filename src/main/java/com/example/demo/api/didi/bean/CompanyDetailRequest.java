package com.example.demo.api.didi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDetailRequest {

    @JsonProperty("company_id")
    private String companyId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("client_id")
    private String clientId;

    private String timestamp;

    private String sign;
}
