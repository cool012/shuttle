package com.example.hope.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @description: 支付宝沙盒配置
 * @author: DHY
 * @created: 2021/02/04 21:17
 */
@Component
public class AlipayConfig implements ApplicationRunner {

    @Value("${alipay.appId}")
    public String app_id;

    @Value("${alipay.publicKey}")
    public String alipay_public_key;

    @Value("${alipay.privateKey}")
    public String merchant_private_key;

    @Value("${alipay.notifyUrl}")
    public String notify_url;

    @Value("${alipay.gateway}")
    public String gatewayUrl;

    public static String return_url = "http://localhost:8081/user/return";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    private AlipayClient client;

    @Override
    public void run(ApplicationArguments args) {
        this.client = new DefaultAlipayClient(this.gatewayUrl, this.app_id, this.merchant_private_key,
                "json", AlipayConfig.charset, this.alipay_public_key, this.sign_type);
    }

    public AlipayClient getAlipayClient() {
        return client;
    }
}
