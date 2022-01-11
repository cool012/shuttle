package com.example.hope.config.redis;

import com.example.hope.service.AdsService;
import com.example.hope.service.serviceIpm.OrderServiceIpm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: DHY
 * @created: 2020/11/14 20:48
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private OrderServiceIpm orderServiceIpm;
    private AdsService adsService;

    @Autowired
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, @Lazy OrderServiceIpm orderService,
                                      @Lazy AdsService adsService) {
        super(listenerContainer);
        this.orderServiceIpm = orderService;
        this.adsService = adsService;
    }

    /**
     * 使用该方法监听 ,当key失效的时候执行该方法
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        long id = Long.parseLong(key.substring(key.indexOf("_") + 1));
        if (key.contains("order")) orderServiceIpm.delete(id);
        else if (key.contains("completed")) orderServiceIpm.completed(id);
        else if (key.contains("ads")) adsService.delete(id);
    }
}