package com.example.demo.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public interface RedisClient {

    //实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)
    long ttl(String key);

    //实现命令：expire 设置过期时间，单位秒
    void expire(String key, long timeout);

    //实现命令：INCR key，增加key一次
    long incr(String key, long delta);

    //实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
    Set<String> keys(String pattern);

    //实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
    void set(String key, String value);

    void set(String key, String value, int timeout);

    Boolean setIfAbsent(String key, String value);

    //实现命令：GET key，返回 key所关联的字符串值。
    String get(String key);

    String getAndset(String key, String value);

    //实现命令：DEL key，删除一个key
    Boolean del(String key);

    //实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
    void hset(String key, String field, String value);

    //实现命令：HGET key field，返回哈希表 key中给定域 field的值
    String hget(String key, String field);

    //实现命令：HDEL key field，删除哈希表 key 中的一个指定域，不存在的域将被忽略。
    void hdel(String key, String fields);

    //实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
    Map<String, String> hgetall(String key);

    long lpush(String key, String value);

    String lpop(String key);

    long rpush(String key, String value);

    List<String> lrange(String key, long begin, long end);

    Long sadd(String key, String... values);

    Set<String> smembers(String key);
}