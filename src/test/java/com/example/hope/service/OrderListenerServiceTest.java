package com.example.hope.service;

import com.example.hope.model.entity.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderListenerServiceTest {
    // 发消息
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void createOrder() {
        Orders order = new Orders();
        order.setId(1);
        amqpTemplate.convertAndSend("order.exchange", "order.created", order);
    }
}