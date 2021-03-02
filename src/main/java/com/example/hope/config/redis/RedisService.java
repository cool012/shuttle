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
public class RedisService {

    private RedisTemplate<String, String> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 向redis添加元素，设置expire
     *
     * @param key  key
     * @param time time
     * @param unit unit
     */
    public void expire(final String key, String value, int time, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, time, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从redis里删除元素
     *
     * @param key key
     */
    public void del(final String key) {
        try {
            if (redisTemplate.hasKey(key)) redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向Zset添加元素
     *
     * @param key   key
     * @param value value
     * @param score score
     */
    public void add(String key, String value, double score) {
        stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * score的增加或减少
     *
     * @param key   key
     * @param value value
     * @param score score
     * @return score
     */
    public double incrScore(String key, String value, double score) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 查询排行榜 0:-1表示获取全部
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return 结果集
     */
    public Set<String> range(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
    }
}
