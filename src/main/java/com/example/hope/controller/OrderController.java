package com.example.hope.controller;

import cn.hutool.json.JSON;
import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.JwtUtils;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.Order;
import com.example.hope.service.OrderService;
import com.example.hope.service.serviceIpm.OrderServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description: 订单相关路由
 * @author: DHY
 * @created: 2020/10/25 14:20
 */
@RestController
@RequestMapping("/order")
@Api(tags = "订单相关接口")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderServiceIpm orderService) {
        this.orderService = orderService;
    }

    @LoginUser
    @ApiOperation("添加订单")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(@RequestBody List<Order> orderList) {
        orderService.insert(orderList);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        orderService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("修改订单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Order order) {
        orderService.update(order);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("服务员接单")
    @RequestMapping(value = "/receive/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> receive(HttpServletRequest request,@PathVariable long id) {
        orderService.receive(id,request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("查询全部订单")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findAll(option));
    }

    @Admin
    @ApiOperation("根据产品id查询订单")
    @RequestMapping(value = "/findByPid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByPid(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByPid(id, option));
    }

    @LoginUser
    @ApiOperation("根据消费者id查询订单")
    @RequestMapping(value = "/findByCid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCid(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByCid(id, option));
    }

    @LoginUser
    @ApiOperation("根据生产者id查询订单")
    @RequestMapping(value = "/findByUid/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByUid(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByUid(id, option));
    }

    @LoginUser
    @ApiOperation("按服务类型查询订单")
    @RequestMapping(value = "/findByType/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByType(@PathVariable long id, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(orderService.findByType(id, option));
    }

    @LoginUser
    @ApiOperation("按订单id查询订单")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable long id) {
        return ReturnMessageUtil.sucess(orderService.findById(id));
    }
}