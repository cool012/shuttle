package com.example.hope.config.elasticsearch;

import com.example.hope.elasticsearch.service.EsProductService;
import com.example.hope.elasticsearch.service.EsStoreService;
import com.example.hope.elasticsearch.service.EsUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 启动初始化es数据
 * @author: DHY
 * @created: 2021/02/28 15:25
 */

@Component
public class ElasticSearchRunner implements ApplicationRunner {

    @Resource
    private EsProductService esProductService;

    @Resource
    private EsUserService esUserService;

    @Resource
    private EsStoreService esStoreService;

    @Override
    public void run(ApplicationArguments args) {
        esProductService.importAll();
        esUserService.importAll();
        esStoreService.importAll();
    }
}
