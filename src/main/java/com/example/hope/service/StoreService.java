package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Store;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface StoreService extends BaseService<Store> {

    boolean insert(Store store);

    boolean delete(long id);

    boolean deleteByCategoryId(long categoryId);

    boolean update(Store store);

    List<Store> rank(Map<String, String> option);

    boolean sales(long id, int quantity);

    SearchHits search(String keyword, Map<String, String> option);

    PageInfo<Store> page(Map<String, String> option);

    PageInfo<Store> findByServiceId(long serviceId, Map<String, String> option);

    List<Store> findByCategoryId(long categoryId);

    PageInfo<Store> findByCategoryId(long categoryId, Map<String, String> option);

    Store detail(long id);

    boolean review(long id, float rate, String token);

    boolean exist(long id);

    List<Store> findByName(String name);
}
