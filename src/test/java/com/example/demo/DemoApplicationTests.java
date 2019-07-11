package com.example.demo;

import com.example.demo.api.didi.DidiClient;
import com.example.demo.api.didi.bean.*;
import com.example.demo.didi.RequestApi;
import com.example.demo.properties.DidiProperties;
import com.example.demo.service.TestService;
import com.example.demo.store.domain.Test;
import com.example.demo.utils.DateUtil;
import com.example.demo.utils.MD5Util;
import com.example.demo.utils.RedisOperator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class DemoApplicationTests {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private TestService testService;
    
    @Autowired
    private RequestApi requestApi;


    @Autowired
    private DidiProperties didiProperties;

    private DidiClient didiClient = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .logLevel(Logger.Level.FULL)
            .target(DidiClient.class, "http://api.es.xiaojukeji.com");


    // 角色查询
    @org.junit.Test
    public void role() throws UnsupportedEncodingException {
        CompanyDetailRequest companyDetailRequest = new CompanyDetailRequest();
        companyDetailRequest.setCompanyId(URLEncoder.encode(didiProperties.getCompanyId(), "utf-8"));
        companyDetailRequest.setClientId(didiProperties.getClientId());
        companyDetailRequest.setAccessToken(client());
        companyDetailRequest.setTimestamp("" + DateUtil.getTimeSecond());
        Map<String, String> map = new HashMap<>();
        String s;
        try{
            s = new ObjectMapper().writeValueAsString(companyDetailRequest);
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(s, new TypeReference<HashMap<String,Object>>(){});
        }catch(Exception e){
            e.printStackTrace();
        }
        companyDetailRequest.setSign(sign(map));
        RolelResponseStatus role = didiClient.getRelo(companyDetailRequest);
        log.info("role = {}", role.getData());
    }


    // 获取企业详情
    @org.junit.Test
    public void detail() {
        CompanyDetailRequest companyDetailRequest = new CompanyDetailRequest();
        companyDetailRequest.setCompanyId(didiProperties.getCompanyId());
        companyDetailRequest.setClientId(didiProperties.getClientId());
        companyDetailRequest.setAccessToken(client());
        companyDetailRequest.setTimestamp("" + DateUtil.getTimeSecond());
        Map<String, String> map = new HashMap<>();
        String s;
        try{
            s = new ObjectMapper().writeValueAsString(companyDetailRequest);
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(s, new TypeReference<HashMap<String,Object>>(){});
        }catch(Exception e){
            e.printStackTrace();
        }
        companyDetailRequest.setSign(sign(map));
        CompanyDetailResponseStatus companyDetail = didiClient.getCompanyDetail(companyDetailRequest);
        log.info("companyDetail = {}", companyDetail);
    }



    // 获取didi token
//    @org.junit.Test
    public String client() {


        String accessToken = redisOperator.get("didi.access_token.key");
        if (org.apache.commons.lang3.StringUtils.isEmpty(accessToken)) {


        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setClientId(didiProperties.getClientId());
        accessTokenRequest.setClientSecret(didiProperties.getClientSecret());
        accessTokenRequest.setGrantType(didiProperties.getGrantType());
        accessTokenRequest.setPhone(didiProperties.getPhone());
        accessTokenRequest.setTimestamp("" + DateUtil.getTimeSecond());
        Map<String, String> map = new HashMap<>();
        String s;
        try{
            s = new ObjectMapper().writeValueAsString(accessTokenRequest);
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(s, new TypeReference<HashMap<String,Object>>(){});
        }catch(Exception e){
            e.printStackTrace();
        }
        accessTokenRequest.setSign(sign(map));
        AccessTokenResponse accessTokens = didiClient.getAccessToken(accessTokenRequest);
        log.info("accessToken = {}", accessToken);


        accessToken = accessTokens.getAccessToken();
            redisOperator.set("didi.access_token.key", accessToken, 1200);
        }
        return accessToken;
    }

    private String sign(Map<String, String> sinParameters) {
        sinParameters.remove("sign");
        sinParameters.put("sign_key", didiProperties.getSignKey());
        List<String> keys = new ArrayList<>(sinParameters.keySet());
        Collections.sort(keys);
        List<String> keyValueList = new ArrayList<>();
        for (String key : keys) {
            String value = sinParameters.get(key);
            if (value != null) {
                keyValueList.add(key + "=" + value);
            }
        }
        String queryString = StringUtils.join(keyValueList, "&");
        String sign = MD5Util.getMD5Str(queryString);
        sinParameters.remove("sign_key");
        return sign;
    }
    
    @org.junit.Test
    public void getCompanyDetail() {
        // 获取didi企业详情 重构前
        Map<String, String> companyDetail = requestApi.companyDetail(client());
        Map<String, String> role = requestApi.role(client());
        System.out.println(companyDetail);
    }
    
    @org.junit.Test
    public void authorizeRiver() {
        // 获取token 授权认证 重构前
        Map<String, String> stringStringMap = requestApi.authorizeRiver();
    }

    @org.junit.Test
    public void test() {
        // 查询test表中数据 测试数据库连接
        List<Test> testList = testService.getTestList();
    }

}
