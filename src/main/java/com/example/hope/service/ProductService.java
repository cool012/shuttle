package com.example.hope.service;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.detail.ProductDetail;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void insert(Product product);

    void delete(long id);

    void update(Product product);

    List<ProductDetail> findAll(Map<String,String> option);

    List<ProductDetail> findAllByType(long serviceId,Map<String,String> option);

    List<ProductDetail> findAllByTypeAndCategory(long serviceId, long categoryId, Map<String,String> option);
}