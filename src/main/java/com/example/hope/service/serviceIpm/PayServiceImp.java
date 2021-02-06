package com.example.hope.service.serviceIpm;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.AlipayConfig;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.service.PayService;
import com.example.hope.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: 支付实现类
 * @author: DHY
 * @created: 2021/02/05 13:21
 */
@Service
public class PayServiceImp implements PayService {

    private AlipayConfig alipayConfig;
    private UserService userService;

    @Autowired
    PayServiceImp(AlipayConfig alipayConfig, UserServiceIpm userServiceIpm) {
        this.alipayConfig = alipayConfig;
        this.userService = userServiceIpm;
    }

    /**
     * 充值
     *
     * @param userId
     * @param total
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String alipay(long userId, double total) throws AlipayApiException {
        AlipayClient client = alipayConfig.getAlipayClient();
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(Utils.getOrderNo(userId));
        model.setBody("模拟充值积分");
        model.setProductCode(String.valueOf(userId));
        model.setSubject("充值");
        model.setTotalAmount(String.valueOf(total));
        request.setBizModel(model);
        request.setNotifyUrl(alipayConfig.notify_url);
        request.setReturnUrl(alipayConfig.return_url);
        String form = client.pageExecute(request).getBody();
        return form;
    }

    /**
     * 同步通知
     *
     * @param request
     * @throws Exception
     */
    @Override
    public void returnCall(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = getParams(requestParams);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.alipay_public_key, alipayConfig.charset,
                alipayConfig.sign_type);
        if (signVerified) {
            long userId = Long.valueOf(params.get("out_trade_no").split("id=")[1]);
            int total = Double.valueOf(params.get("total_amount")).intValue();
            userService.addScore(userId, total);
        } else {
            BusinessException.check(0, "充值失败");
        }
    }

    /**
     * 异步通知
     *
     * @param request
     * @throws Exception
     */
    @Override
    public void notifyCall(HttpServletRequest request) throws Exception {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = getParams(requestParams);
        String tradeStatus = params.get("trade_status");
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.alipay_public_key, alipayConfig.charset,
                alipayConfig.sign_type);
        if (signVerified) {
            if (tradeStatus.equals("TRADE_FINISHED")) {
                System.out.println("TRADE_FINISHED");
            } else if (tradeStatus.equals("TRADE_SUCCESS")) System.out.println("TRADE_SUCCESS");
        } else {
            System.out.println("notify sign failed");
        }
    }

    private Map<String, String> getParams(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
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
