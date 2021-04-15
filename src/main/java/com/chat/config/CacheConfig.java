package com.chat.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class CacheConfig {
    /**
     * 自定义 Cache 的 key 生成器
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(":").append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
