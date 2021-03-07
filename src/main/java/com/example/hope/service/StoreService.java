package com.example.hope.service;

import com.example.hope.model.entity.Store;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface StoreService {

    void insert(Store store);

    void delete(long id);

    void update(Store store);

    List<Store> rank();

    void sales(long id, int quantity);

    SearchHits search(String keyword, Map<String, String> option);

    PageInfo<Store> findAll(Map<String, String> option);

    PageInfo<Store> findByServiceId(long serviceId, Map<String, String> option);

    List<Store> findByCategoryId(long categoryId);

    List<Store> findById(long id);

    void review(long id, float rate);

    boolean exist(long id);
}
