package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Star;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface StarService extends BaseService<Star> {

    boolean insert(Star star,String token);

    boolean delete(Star star, String token);

    PageInfo<Star> findByStore(String token, Map<String, String> option);

    PageInfo<Star> findByProduct(String token, Map<String, String> option);

    boolean isStarByStoreId(String token, long storeId);

    boolean isStarByProductId(String token, long productId);
}