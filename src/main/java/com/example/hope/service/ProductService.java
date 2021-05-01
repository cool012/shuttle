package com.example.hope.service;

import com.example.hope.model.entity.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void insert(Product product);

    void delete(long id);

    void update(Product product);

    void review(Product product, String token, long orderId);

    List<Product> rank(Map<String, String> option);

    SearchHits<Product> search(String keyword, Map<String, String> option);

    PageInfo<Product> findAll(Map<String, String> option);

    List<Product> findByStoreId(long storeId);

    PageInfo<Product> findByStoreId(long storeId, Map<String, String> option);

    Product findById(long id);

    boolean exist(long id);
}