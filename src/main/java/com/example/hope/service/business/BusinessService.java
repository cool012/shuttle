package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Business;

import java.util.List;

public interface BusinessService extends BaseService<Business> {

    boolean insert(Business business);

    boolean delete(Long id);

    boolean update(Business business);

    boolean exist(long id);

    Business get(long id);

    IPage<Business> selectByPage(Query query);

    List<Business> getList();
}