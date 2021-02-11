package com.example.hope.service;

import com.example.hope.model.entity.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void insert(Product product);

    void delete(long id);

    void update(Product product);

    void review(long id, int rate);

    List<Product> rank();

    List<Product> search(String keyword);

    PageInfo<Product> findAll(Map<String, String> option);

    List<Product> findByStoreId(long storeId);

    Product findById(long id);
}