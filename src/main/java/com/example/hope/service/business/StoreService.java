package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Store;
import com.example.hope.model.vo.StoreVO;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

public interface StoreService extends BaseService<Store> {

    boolean insert(Store store);

    boolean delete(long id);

    boolean deleteByCategoryId(long categoryId);

    boolean update(Store store);

    boolean sales(long id, int quantity);

    boolean review(long id, float rate, String token);

    boolean exist(long id);

    IPage<StoreVO> page(Query query);

    IPage<StoreVO> findByServiceId(long serviceId, Query query);

    IPage<StoreVO>findByCategoryId(long categoryId, Query query);

    List<Store> rank(Map<String, String> option);

    List<Store> findByCategoryId(long categoryId);

    StoreVO detail(long id);

    StoreVO findByName(String name);

    SearchHits<Store> search(String keyword, Map<String, String> option);

}
