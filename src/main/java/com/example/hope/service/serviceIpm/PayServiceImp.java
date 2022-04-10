package com.example.hope.service.serviceIpm;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.alipay.AlipayConfig;
import com.example.hope.service.PayService;
import com.example.hope.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 支付实现类
 * @author: DHY
 * @created: 2021/02/05 13:21
 */
@Service
public class PayServiceImp implements PayService {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private UserService userService;

    // 重定向url，前端支付结果界面
    @Value("${alipay.redirectUrl}")
    private String redirectUrl;

    /**
     * 充值
     *
     * @param userId 用户id
     * @param total  充值总金额
     * @return 支付宝sdk表单
     * @throws AlipayApiException AlipayApiException
     */
    @Override
    public String alipay(long userId, double total) throws AlipayApiException {
        AlipayClient client = alipayConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(Utils.getOrderNo());
        model.setBody(String.valueOf(userId));
        model.setSubject("充值");
        model.setTotalAmount(String.valueOf(total));
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.notify_url);
        request.setReturnUrl(alipayConfig.return_url);
        return client.pageExecute(request).getBody();
    }

    /**
     * 同步通知
     * 支付成功返回前端支付成功页面
     *
     * @param request HttpServletRequest
     * @throws Exception Exception
     */
    @Override
    public String returnCall(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = getParams(requestParams);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type);
        String redirectUrl;
        if (this.redirectUrl.equals("")) redirectUrl = alipayConfig.redirect_url;
        else redirectUrl = String.format("http://%s/result/", this.redirectUrl);
        if (!signVerified) return redirectUrl + "0";
        return redirectUrl + "1";
    }

    /**
     * 异步通知
     * 支付宝服务器回调公网ip，执行相关逻辑
     *
     * @param request HttpServletRequest
     * @throws Exception Exception
     */
    @Override
    public void notifyCall(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = getParams(requestParams);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.alipay_public_key, AlipayConfig.charset,
                AlipayConfig.sign_type);
        if (signVerified) {
            long userId = Long.parseLong(params.get("body"));
            int total = Double.valueOf(params.get("total_amount")).intValue();
            userService.addScore(userId, total);
        }
    }

    /**
     * 封装获取request参数
     *
     * @param requestParams request参数
     * @return 结果集
     */
    private Map<String, String> getParams(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
}
