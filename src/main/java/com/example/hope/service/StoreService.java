package com.example.hope.service;

import com.example.hope.model.entity.Store;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface StoreService {

    void insert(Store store);

    void delete(long id);

    void update(Store store);

    PageInfo<Store> findAll(Map<String, String> option);

    List<Store> findByServiceId(long serviceId);

    List<Store> findByCategoryId(long categoryId);

    Store findById(long id);

    List<Store> rank();

    void sales(long id, int quantity);

    List<Store> search(String keyword);
}
