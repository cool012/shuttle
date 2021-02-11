package com.example.hope.config.redis;

import com.example.hope.service.serviceIpm.OrderServiceIpm;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, OrderServiceIpm orderService) {
        super(listenerContainer);
        this.orderServiceIpm = orderService;
    }

    /**
     * 使用该方法监听 ,当key失效的时候执行该方法
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();//todo 清空缓存判断
        int id = Integer.parseInt(key.substring(key.indexOf("_") + 1));
        if (key.contains("order")) {
            orderServiceIpm.delete(id);
        }
        if(key.contains("completed")){
            orderServiceIpm.completed(id);
        }
    }
}
