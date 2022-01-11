package com.example.hope.config.elasticsearch;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.Store;
import com.example.hope.model.entity.User;
import com.example.hope.repository.elasticsearch.ProductRepository;
import com.example.hope.repository.elasticsearch.StoreRepository;
import com.example.hope.repository.elasticsearch.UserRepository;
import com.example.hope.service.ProductService;
import com.example.hope.service.StoreService;
import com.example.hope.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 启动初始化es数据
 * @author: DHY
 * @created: 2021/02/28 15:25
 */
@Component
public class ElasticSearchRunner implements ApplicationRunner {

//    @Resource
//    private ProductRepository productRepository;
//
//    @Resource
//    private UserRepository userRepository;
//
//    @Resource
//    private StoreRepository storeRepository;
//
//    @Resource
//    private ProductService productService;
//
//    @Resource
//    private StoreService StoreService;
//
//    @Resource
//    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
//        productImportAll();
//        StoreImportAll();
//        userImportAll();
    }

//    public void productImportAll() {
//        List<Product> products = productService.list();
//        productRepository.saveAll(products);
//    }
//
//    public void StoreImportAll() {
//        List<Store> Stores = StoreService.list();
//        storeRepository.saveAll(Stores);
//    }
//
//    public void userImportAll() {
//        List<User> products = userService.findAll(new HashMap<>()).getList();
//        userRepository.saveAll(products);
//    }
}