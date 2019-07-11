package com.example.demo.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public class RedisTemplateClient implements RedisClient {

    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public RedisTemplateClient(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, int timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndset(String key, String value) {
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public long incr(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public String hget(String key, String field) {
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public void hdel(String key, String fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public String lpop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public List<String> lrange(String key, long begin, long end) {
        return redisTemplate.opsForList().range(key, begin, end);
    }

    @Override
    public Long sadd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<String> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}