package com.example.hope.service.business;

import com.example.hope.model.entity.ApproveProduct;
import com.example.hope.model.entity.ApproveStore;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ApproveService {

    void insertStore(ApproveStore approveStore, String token);

    void insertProduct(ApproveProduct approveProduct, String token);

    void approveStore(ApproveStore approveStore);

    void approveProduct(ApproveProduct approveProduct);

    PageInfo<ApproveStore> findAllStore(Map<String, String> option);

    PageInfo<ApproveProduct> findAllProduct(Map<String, String> option);

    List<String> findStoreByUserId(long userId, String token);
}