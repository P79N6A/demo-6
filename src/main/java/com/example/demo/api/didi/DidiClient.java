package com.example.demo.api.didi;

import com.example.demo.api.didi.bean.*;
import feign.Headers;
import feign.RequestLine;

public interface DidiClient {
    /**
     * 获取token 授权认证
     * 
     * @param accessTokenRequest 请求参数
     * @return com.example.demo.api.didi.bean.AccessTokenResponse
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("POST /river/Auth/authorize")
    AccessTokenResponse getAccessToken(AccessTokenRequest accessTokenRequest);

    /**
     * 获取企业详情
     *
     * @param companyDetailRequest 请求参数
     * @return com.example.demo.api.didi.bean.CompanyDetailResponseStatus
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("GET /river/Company/detail")
    CompanyDetailResponseStatus getCompanyDetail(CompanyDetailRequest companyDetailRequest);

    /**
     * 角色查询
     *
     * @param companyDetailRequest 请求参数
     * @return com.example.demo.api.didi.bean.RolelResponseStatus
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @RequestLine("GET /river/Role/get")
    RolelResponseStatus getRelo(CompanyDetailRequest companyDetailRequest);
}
