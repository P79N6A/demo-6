package com.example.demo.didi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface DidiService {

    /**
     * 接口认证 接口获取授权后的access token
     * @return
     */
    String getAccessToken();

    /**
     * 企业详情
     * @return
     */
    JSONObject getCompanyDetail();

    JSONObject getRegulation();

    JSONArray getRole();

    JSONObject getMemberDetail(String memberId);

    JSONObject singleMember(JSONObject jsonObject);

    JSONObject editMember(JSONObject jsonObject);

    JSONObject delMember(String memberId);

    JSONObject getMember(JSONObject jsonObject);

    JSONObject orderDetail(String orderId);

    JSONObject getOrder(JSONObject jsonObject);

}
