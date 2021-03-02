package com.example.hope.config.redis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis初始化数据
 * @author: DHY
 * @created: 2021/03/02 13:02
 */

@Component
public class RedisRunner implements ApplicationRunner {

    @Resource
    private RedisService redisService;

    @Override
    public void run(ApplicationArguments args) {
        redisService.expire("ads_1", "expire", 10, TimeUnit.DAYS);
        redisService.expire("ads_2", "expire", 10, TimeUnit.DAYS);
    }
}
