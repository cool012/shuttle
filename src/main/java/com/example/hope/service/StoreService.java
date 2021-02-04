package com.example.hope.service;

import com.example.hope.model.entity.Store;

import java.util.List;

public interface StoreService {

    void insert(Store store);

    void delete(long id);

    void update(Store store);

    List<Store> findAll();

    List<Store> findByServiceId(long serviceId);

    List<Store> findByCategoryId(long categoryId);

    Store findById(long id);

    List<Store> rank();

    void sales(long id, int quantity);

    List<Store> search(String keyword);
}
