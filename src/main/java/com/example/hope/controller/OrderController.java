package com.example.hope.controller;

import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.Order;
import com.example.hope.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 订单相关路由
 * @author: DHY
 * @created: 2020/10/25 14:20
 */
@RestController
@RequestMapping("/order")
@Api("订单相关接口")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @ApiOperation("添加订单")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Order order) {
        orderService.insert(order);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        orderService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("修改订单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Order order) {
        orderService.update(order);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("查询全部订单")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll() {
        return ReturnMessageUtil.sucess(orderService.findAll());
    }

    @ApiOperation("根据产品id查询订单")
    @RequestMapping(value = "/findByPid", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPid(long id) {
        return ReturnMessageUtil.sucess(orderService.findByPid(id));
    }

    @ApiOperation("根据消费者id查询订单")
    @RequestMapping(value = "/findByCid", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCid(long id) {
        return ReturnMessageUtil.sucess(orderService.findByCid(id));
    }

    @ApiOperation("根据生产者id查询订单")
    @RequestMapping(value = "/findByUid", method = RequestMethod.GET)
    public ReturnMessage<Object> findByUid(long id) {
        return ReturnMessageUtil.sucess(orderService.findByUid(id));
    }
}
