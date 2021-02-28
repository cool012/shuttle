package com.example.hope.elasticsearch.service;

import com.example.hope.model.entity.Store;

import java.util.List;

public interface EsStoreService {

    void importAll();

    void save(Store Store);

    void delete(long StoreId);

    List<Store> search(String keyword);
}
