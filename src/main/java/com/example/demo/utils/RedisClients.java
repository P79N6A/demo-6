package com.example.demo.utils;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public class RedisClients {

    public static final class Builder {

        private RedisClient redisClient;
        private InterceptorChain interceptorChain = new InterceptorChain();

        public Builder setRedisClient(JedisPool pool) {
            this.redisClient = new JedisClient(pool);
            return this;
        }

        public Builder setRedisClient(RedisTemplate redisClient, StringRedisTemplate stringRedisTemplate) {
            this.redisClient = new RedisTemplateClient(redisClient, stringRedisTemplate);
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptorChain.addInterceptor(interceptor);
            return this;
        }

        public RedisClient build() {
            if (interceptorChain.getInterceptors().size() == 0 || !interceptorChain.hasInterceptorByClass(RedisInterceptor.class)) {
                interceptorChain.addInterceptor(new RedisInterceptor());
            }
            return (RedisClient) Proxy.newProxyInstance(redisClient.getClass().getClassLoader(),
                    new Class[]{RedisClient.class},
                    new RedisInvocationHandler(redisClient, interceptorChain));
        }
    }
}