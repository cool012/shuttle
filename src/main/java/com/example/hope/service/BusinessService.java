package com.example.hope.service;

import com.example.hope.base.service.BaseService;
import com.example.hope.model.entity.Business;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BusinessService extends BaseService<Business> {

    boolean insert(Business business);

    boolean delete(Long id);

    boolean update(Business business);

    PageInfo<Business> page(Map<String, String> option);

    Business get(long id);

    boolean exist(long id);

    List<Business> getList();
}