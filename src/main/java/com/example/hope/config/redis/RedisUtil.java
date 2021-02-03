package com.example.hope.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis工具类
 * @author: DHY
 * @created: 2020/11/14 21:07
 */
@Component
public class RedisUtil {

    private RedisTemplate<String, String> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 向redis添加元素
     *
     * @param key
     * @param value
     * @param time
     * @param unit
     * @return
     */
    public boolean ins(final String key, String value, int time, TimeUnit unit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, time, unit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向Zset添加元素
     *
     * @param key
     * @param value
     * @param score
     */
    public void add(String key, String value, double score) {
        stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * score的增加或减少
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public double incrScore(String key, String value, double score) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 查询排行榜 0:-1表示获取全部
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> range(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
    }
}
