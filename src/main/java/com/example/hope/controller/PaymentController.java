package com.example.hope.controller;

import com.example.hope.service.PayService;
import com.example.hope.service.serviceIpm.PayServiceImp;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 支付相关路由
 * @author: DHY
 * @created: 2021/02/09 19:33
 */
@Controller
@RequestMapping("/payment")
@Api(tags = "支付相关接口")
public class PaymentController {

    private PayService payService;

    @Autowired
    PaymentController(PayServiceImp payServiceImp) {
        this.payService = payServiceImp;
    }

    /**
     * showdoc
     *
     * @catalog 支付
     * @title 同步通知
     * @description 同步通知的接口
     * @method get
     * @url /payment/return
     */
    @RequestMapping("/return")
    @ResponseBody
    public void returnCall(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String redirectUrl = payService.returnCall(request);
        response.sendRedirect(redirectUrl);
    }

    /**
     * showdoc
     *
     * @catalog 支付
     * @title 异步通知
     * @description 异步通知的接口
     * @method get
     * @url /payment/notify
     */
    @RequestMapping("/notify")
    @ResponseBody
    public void notifyCall(HttpServletRequest request) throws Exception {
        payService.notifyCall(request);
    }
}