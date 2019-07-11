package com.example.demo.api.didi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolelResponseStatus {

    private int errno;

    private String errmsg;

    private List<RoleResponse> data;

    @JsonProperty("request_id")
    private String requestId;
}
