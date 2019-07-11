package com.example.demo.api.didi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDetailResponseStatus {

    private int errno;

    private String errmsg;

    private CompanyDetailResponse data;

    @JsonProperty("request_id")
    private String requestId;
}
