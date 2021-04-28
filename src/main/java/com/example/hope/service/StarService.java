package com.example.hope.service;

import com.example.hope.model.entity.Star;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface StarService {

    void insert(Star star,String token);

    void delete(Star star, String token);

    PageInfo<Star> findByStore(String token, Map<String, String> option);

    PageInfo<Star> findByProduct(String token, Map<String, String> option);

    boolean isStarByStoreId(String token, long storeId);

    boolean isStarByProductId(String token, long productId);
}