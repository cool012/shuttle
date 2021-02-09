package com.example.hope.service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;

public interface PayService {

    String alipay(long userId, double total) throws AlipayApiException;

    String returnCall(HttpServletRequest request) throws Exception;

    void notifyCall(HttpServletRequest request) throws Exception;
}
