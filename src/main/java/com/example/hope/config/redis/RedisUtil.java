package com.example.hope.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description: redis工具类
 * @author: DHY
 * @created: 2020/11/14 21:07
 */
@Component
public class RedisUtil {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public boolean ins(final String key, String value, int time, TimeUnit unit){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value,time,unit);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
