package com.example.demo.didi;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.demo.properties.DidiProperties;
import com.example.demo.utils.DateUtil;
import com.example.demo.utils.HttpClient;
import com.example.demo.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 请求代理类
 */
@Component
public class RequestApi {


    @Autowired
    private DidiProperties didiProperties;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 默认请求超时时间(ms)
     */
    public final static int DEFAULT_TIMEOUT_SOCKET = 2000;
    public final static int DEFAULT_TIMEOUT_CONNECTION = 1000;
    public final static int DEFAULT_TIMEOUT_CONNECTION_REQUEST = 1000;

    public static final String CLIENT_SECRET = "client_secret";
    public static final String GRANT_TYPE = "grant_type";
    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 叫车类接口授权认证
     * ;     * @return 授权认证信息
     */
    public Map<String, String> authorizeV1() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put(CLIENT_SECRET, didiProperties.getClientSecret());
        paramMap.put(GRANT_TYPE, didiProperties.getGrantType());
        paramMap.put("phone", didiProperties.getPhone());
        return post("/v1/Auth/authorize", paramMap);
    }

    /**
     * 管理类接口授权认证
     *
     * @return 授权认证信息
     */
    public Map<String, String> authorizeRiver() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put(CLIENT_SECRET, didiProperties.getClientSecret());
        paramMap.put(GRANT_TYPE, didiProperties.getGrantType());
        paramMap.put("phone", didiProperties.getPhone());
        return post("/river/Auth/authorize", paramMap);
    }


    /**
     * 企业详情
     *
     * @return 企业详情
     */
    public Map<String, String> companyDetail(String accessToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        return get("/river/Company/detail", paramMap);
    }

    /**
     * 获取用车规则
     *
     * @return 用车规则
     */
    public Map<String, String> regulation(String accessToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        return get("/river/Regulation/get", paramMap);
    }

    /**
     * 角色查询
     *
     * @return 返回角色信息
     */
    public Map<String, String> role(String accessToken) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put(CLIENT_SECRET, didiProperties.getClientSecret());
        paramMap.put(GRANT_TYPE, didiProperties.getGrantType());
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        return get("/river/Role/get", paramMap);
    }

    /**
     * 根据员工号查询员工详情
     *
     * @param accessToken token
     * @param memberId    员工企业Id
     * @return
     */
    public Map<String, String> detailMember(String accessToken, String memberId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put("member_id", memberId);
        paramMap.put(ACCESS_TOKEN, accessToken);
        return get("/river/Member/detail", paramMap);
    }

    /**
     * 查询所有员工信息接口
     *
     * @param accessToken
     * @param paramJson
     * @return 分页返回员工信息接口
     */
    public Map<String, String> getMember(String accessToken, JSONObject paramJson) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        paramMap.putAll(JSONObject.toJavaObject(paramJson, Map.class));
        return get("/river/Member/get", paramMap);
    }


    /**
     * 新增员工信息接口
     *
     * @param accessToken
     * @param paramJson
     * @return 分页返回员工信息接口
     */
    public Map<String, String> singleMember(String accessToken, JSONObject paramJson) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put("data", paramJson.toJSONString());
        paramMap.put(ACCESS_TOKEN, accessToken);
        return post("/river/Member/single", paramMap);
    }


    /**
     * 修改员工信息
     *
     * @param accessToken
     * @param paramJson
     * @return 返回结果是否修改成功
     */
    public Map<String, String> editMember(String accessToken, JSONObject paramJson) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put("member_id", paramJson.getString("memberId"));
        paramMap.put("data", paramJson.toJSONString());
        paramMap.put(ACCESS_TOKEN, accessToken);
        return post("/river/Member/single", paramMap);
    }

    /**
     * 删除员工信息
     *
     * @param accessToken
     * @param memberId
     * @return 返回删除的员工
     */
    public Map<String, String> delMember(String accessToken, String memberId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put("member_id", memberId);
        paramMap.put(ACCESS_TOKEN, accessToken);
        return post("/river/Member/single", paramMap);
    }


    /**
     * 分页查询订单信息接口
     *
     * @param accessToken
     * @param paramJson
     * @return 返回订单数据接口
     */
    public Map<String, String> getOrder(String accessToken, JSONObject paramJson) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        paramMap.putAll(JSONObject.toJavaObject(paramJson, Map.class));
        return get("/river/Order/get", paramMap);
    }

    /**
     * 根据订单ID查询订单详情
     *
     * @param accessToken
     * @param orderId
     * @return 返回订单数据接口
     */
    public Map<String, String> orderDetail(String accessToken, String orderId) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("company_id", didiProperties.getCompanyId());
        paramMap.put(ACCESS_TOKEN, accessToken);
        paramMap.put("order_id", orderId);
        return get("/river/Order/detail", paramMap);
    }

    /**
     * get请求
     *
     * @param uri      接口地址
     * @param paramMap 请求参数
     * @return map格式返回值
     */
    public Map<String, String> get(String uri, Map<String, String> paramMap) {
        return request(HttpClient.HTTP_METHOD_GET, uri, paramMap, DEFAULT_TIMEOUT_SOCKET,
                DEFAULT_TIMEOUT_CONNECTION,
                DEFAULT_TIMEOUT_CONNECTION_REQUEST);
    }


    /**
     * get请求
     *
     * @param uri                      接口地址
     * @param paramMap                 请求参数
     * @param timeoutSocket            从服务器读取数据的timeout(ms)
     * @param timeoutConnection        和服务器建立连接的timeout(ms)
     * @param timeoutConnectionRequest 从连接池获取连接的timeout(ms)
     * @return map格式返回值
     */
    public Map<String, String> get(String uri, Map<String, String> paramMap, int timeoutSocket, int timeoutConnection
            , int timeoutConnectionRequest) {
        return request(HttpClient.HTTP_METHOD_GET, uri, paramMap, timeoutSocket, timeoutConnection,
                timeoutConnectionRequest);
    }

    /**
     * post请求
     *
     * @param uri      接口地址
     * @param paramMap 请求参数
     * @return map格式返回值
     */
    public Map<String, String> post(String uri, Map<String, String> paramMap) {
        return request(HttpClient.HTTP_METHOD_POST, uri, paramMap, DEFAULT_TIMEOUT_SOCKET, DEFAULT_TIMEOUT_CONNECTION,
                DEFAULT_TIMEOUT_CONNECTION_REQUEST);
    }

    /**
     * post请求
     *
     * @param uri                      接口地址
     * @param paramMap                 请求参数
     * @param timeoutSocket            从服务器读取数据的timeout(ms)
     * @param timeoutConnection        和服务器建立连接的timeout(ms)
     * @param timeoutConnectionRequest 从连接池获取连接的timeout(ms)
     * @return map格式返回值
     */
    public Map<String, String> post(String uri, Map<String, String> paramMap, int timeoutSocket,
                                    int timeoutConnection, int timeoutConnectionRequest) {
        return request(HttpClient.HTTP_METHOD_POST, uri, paramMap, timeoutSocket, timeoutConnection,
                timeoutConnectionRequest);
    }

    /**
     * 请求（get/post）
     *
     * @param method                   请求方法（get/post）
     * @param uri                      接口uri
     * @param paramMap                 参数
     * @param timeoutSocket            从服务器读取数据的timeout(ms)
     * @param timeoutConnection        和服务器建立连接的timeout(ms)
     * @param timeoutConnectionRequest 从连接池获取连接的timeout(ms)
     * @return 请求结果
     */
    public Map<String, String> request(String method, String uri, Map<String, String> paramMap, int timeoutSocket,
                                       int timeoutConnection, int timeoutConnectionRequest) {
        String url = didiProperties.getHost() + uri;

        addCommonParamMap(paramMap);
        sign(paramMap);

        JSONObject json = new JSONObject();
        json.put("data",paramMap);
        JSONObject data = json.getJSONObject("data");
        HttpEntity<JSONObject> jsonObjectHttpEntity = new HttpEntity<>(data);
//        Object result = restTemplate.postForEntity(url, jsonObjectHttpEntity, new JSONObject().getClass()).getBody();

        String content = HttpClient.request(method, url, paramMap, timeoutSocket, timeoutConnection,
                timeoutConnectionRequest);

        return JSON2Map(content);
    }

    /**
     * 签名计算
     *
     * @param sinParameters
     */
    private void sign(Map<String, String> sinParameters) {
        sinParameters.put("sign_key", didiProperties.getSignKey());

        List<String> keys = new ArrayList<String>(sinParameters.keySet());
        Collections.sort(keys);

        List<String> keyValueList = new ArrayList<String>();
        for (String key : keys) {
            String value = sinParameters.get(key);
            if (value != null) {
                keyValueList.add(key + "=" + value);
            }
        }
        String queryString = StringUtils.join(keyValueList, "&");
        String sign = MD5Util.getMD5Str(queryString);

        sinParameters.put("sign", sign);
        sinParameters.remove("sign_key");

    }

    /**
     * json转map
     *
     * @return
     */
    private static Map<String, String> JSON2Map(String jsonMapStr) {
        Map<String, String> jsonMap = JSONObject.parseObject(jsonMapStr, new TypeReference<Map<String, String>>() {
        });
        return jsonMap;
    }

    /**
     * 添加通用参数
     *
     * @param paramMap
     */
    private void addCommonParamMap(Map<String, String> paramMap) {
        if (null == paramMap) {
            paramMap = new HashMap<>();
        }
        paramMap.put("client_id", didiProperties.getClientId());
        paramMap.put("timestamp", "" + DateUtil.getTimeSecond());
    }


}
