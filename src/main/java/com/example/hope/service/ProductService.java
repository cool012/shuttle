package com.example.hope.service;

import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.detail.ProductDetail;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    void insert(Product product);

    void delete(long id);

    void update(Product product);

    PageInfo<ProductDetail> findAll(Map<String,String> option);

    PageInfo<ProductDetail> findAllByType(long serviceId,Map<String,String> option);

    PageInfo<ProductDetail> findAllByTypeAndCategory(long serviceId, long categoryId, Map<String,String> option);
}