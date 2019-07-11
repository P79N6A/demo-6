package com.example.demo.didi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.didi.DidiService;
import com.example.demo.didi.RequestApi;
import com.example.demo.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: huangjunhong
 * @Date: 2018/12/11 11:45
 * @Description:
 */
@Service
public class DidiServiceImpl implements DidiService {


    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private RequestApi requestApi;

    @Override
    public String getAccessToken() {

        String accessToken = redisOperator.get("didi.access_token.key");
        if (StringUtils.isEmpty(accessToken)) {
            Map<String, String> authorizeMap = requestApi.authorizeRiver();
            accessToken = authorizeMap.get("access_token");
            redisOperator.set("didi.access_token.key", accessToken, 1200);
        }
        return accessToken;

    }

    @Override
    public JSONObject getCompanyDetail() {
        String accessTken = getAccessToken();
        Map<String, String> companyDetailMap = this.requestApi.companyDetail(accessTken);
        if ("0".equals(companyDetailMap.get("errno"))) {
            return JSONObject.parseObject(companyDetailMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONObject getRegulation() {
        String accessTken = getAccessToken();
        Map<String, String> companyDetailMap = this.requestApi.regulation(accessTken);
        if ("0".equals(companyDetailMap.get("errno"))) {
            return JSONObject.parseObject(companyDetailMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONArray getRole() {
        String accessTken = getAccessToken();
        Map<String, String> companyDetailMap = this.requestApi.role(accessTken);
        if ("0".equals(companyDetailMap.get("errno"))) {
            return JSONArray.parseArray(companyDetailMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONObject getMemberDetail(String memberId) {
        String accessTken = getAccessToken();
        Map<String, String> memberDetailMap = this.requestApi.detailMember(accessTken, memberId);
        if ("0".equals(memberDetailMap.get("errno"))) {
            return JSONObject.parseObject(memberDetailMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONObject singleMember(JSONObject jsonObject) {
        String accessTken = getAccessToken();
        Map<String, String> singleMemberMap = this.requestApi.singleMember(accessTken, jsonObject);
        if ("0".equals(singleMemberMap.get("errno"))) {
            return JSONObject.parseObject(singleMemberMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONObject editMember(JSONObject jsonObject) {
        String accessTken = getAccessToken();
        Map<String, String> singleMemberMap = this.requestApi.editMember(accessTken, jsonObject);
        if ("0".equals(singleMemberMap.get("errno"))) {
            return JSONObject.parseObject(singleMemberMap.get("data"));
        }
        return null;
    }

    @Override
    public JSONObject delMember(String memberId) {
        String accessTken = getAccessToken();
        Map<String, String> delMemberMap = this.requestApi.delMember(accessTken, memberId);
        return JSONObject.parseObject(JSON.toJSONString(delMemberMap));
    }

    /**
     * 分页查询员工信息接口
     *
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject getMember(JSONObject jsonObject) {
        String accessTken = getAccessToken();
        Map<String, String> memberDetailMap = this.requestApi.getMember(accessTken, jsonObject);
        return JSONObject.parseObject(JSON.toJSONString(memberDetailMap));
    }

    @Override
    public JSONObject orderDetail(String orderId) {
        String accessTken = getAccessToken();
        Map<String, String> orderDetailMap = this.requestApi.orderDetail(accessTken, orderId);
        return JSONObject.parseObject(JSON.toJSONString(orderDetailMap));
    }

    @Override
    public JSONObject getOrder(JSONObject jsonObject) {
        String accessTken = getAccessToken();
        Map<String, String> orderMap = this.requestApi.getOrder(accessTken, jsonObject);
        return JSONObject.parseObject(JSON.toJSONString(orderMap));
    }


}