package com.example.hope.service;

import com.example.hope.elasticsearch.repository.ProductRepository;
import com.example.hope.model.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description: product
 * @author: DHY
 * @created: 2021/02/27 17:51
 */
@SpringBootTest
public class ProductSearch {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void create(){
        Product product = new Product();
        product.setName("product");
        productRepository.save(product);
    }
}
