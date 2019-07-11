package com.example.demo.utils;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: 邹凌峰
 * @create: 2018-08-19
 **/
public class InterceptorChain {

    private List<Interceptor> interceptors = Lists.newArrayList();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public List<Interceptor> getInterceptors() {
        return Collections.unmodifiableList(this.interceptors);
    }

    public boolean hasInterceptorByClass(Class clazz) {
        for (Interceptor interceptor : interceptors) {
            if (interceptor.getClass() == clazz) {
                return true;
            }
        }
        return false;
    }

    public void preProcess(Object target) {
        for (int i = 0; i < interceptors.size(); i++) {
            interceptors.get(i).before(target);
        }
    }

    public void postProcess(Object target) {
        for (int i = interceptors.size() - 1; i >= 0; --i) {
            interceptors.get(i).after(target);
        }
    }

    public void processException(Object target, Exception e) {
        for (int i = 0; i < interceptors.size(); i++) {
            interceptors.get(i).processException(target, e);
        }
    }
}