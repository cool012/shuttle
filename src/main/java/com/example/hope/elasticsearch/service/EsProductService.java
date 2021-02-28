package com.example.hope.elasticsearch.service;

import com.example.hope.model.entity.Product;

import java.util.List;

public interface EsProductService {

    void importAll();

    void save(Product product);

    void delete(long productId);

    List<Product> search(String keyword);
}
