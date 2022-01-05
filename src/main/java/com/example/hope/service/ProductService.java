package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface ProductService extends BaseService<Product> {

    boolean insert(Product product);

    boolean delete(long id);

    boolean deleteByStoreId(long storeId);

    boolean update(Product product);

    boolean review(Product product, String token, long orderId);

    boolean addSales(long id, int sales);

    List<Product> rank(Map<String, String> option);

    SearchHits<Product> search(String keyword, Map<String, String> option);

    PageInfo<Product> page(Map<String, String> option);

    List<Product> findByStoreId(long storeId);

    PageInfo<Product> findByStoreId(long storeId, Map<String, String> option);

    Product findById(long id);

    boolean exist(long id);
}