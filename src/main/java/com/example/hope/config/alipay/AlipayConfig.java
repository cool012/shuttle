package com.example.hope.config.alipay;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.example.hope.common.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

/**
 * @description: 支付宝沙盒配置
 * @author: DHY
 * @created: 2021/02/04 21:17
 */
@Component
public class AlipayConfig implements ApplicationRunner {

    @Value("${alipay.appId}")
    public String app_id;

    public String notify_url;

    @Value("${alipay.gateway}")
    public String gatewayUrl;

    public String return_url;

    @Value("${server.port}")
    public String http_port;

    public String redirect_url;

    public String alipay_public_key;

    public String merchant_private_key;

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    private AlipayClient client;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        this.merchant_private_key = Utils.getKey("private.txt");
        this.alipay_public_key = Utils.getKey("public.txt");
        String ip = Objects.requireNonNull(new RestTemplate().getForEntity("http://ip-api.com/json",
                JSONObject.class).getBody()).getStr("query");
        this.notify_url = String.format("http://%s:%s/payment/notify", ip, this.http_port);
        this.return_url = String.format("http://%s:%s/payment/return", ip, this.http_port);
        this.redirect_url = String.format("http://%s/result/", ip);
        this.client = new DefaultAlipayClient(this.gatewayUrl, this.app_id, this.merchant_private_key, "json",
                AlipayConfig.charset, this.alipay_public_key, sign_type);
    }

    public AlipayClient getAlipayClient() {
        return client;
    }
}
