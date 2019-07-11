package com.example.demo.api.didi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDetailResponse {

    private String name;

    @JsonProperty("use_credit_count")
    private float useCreditCount;

    @JsonProperty("invoice_count")
    private float invoiceCount;

    @JsonProperty("admin_name")
    private String adminName;

    @JsonProperty("admin_phone")
    private String adminPhone;

    @JsonProperty("auth_status")
    private int authStatus;
}
