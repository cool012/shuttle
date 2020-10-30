package com.example.hope.service;

import com.example.hope.model.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void insert(Product product);

    void delete(long id);

    void update(Product product);

    List<Product> findAll(Map<String,String> option);

    List<Product> findAllByType(long serviceId,Map<String,String> option);

    List<Product> findAllByTypeAndCategory(long serviceId, long categoryId,Map<String,String> option);
}