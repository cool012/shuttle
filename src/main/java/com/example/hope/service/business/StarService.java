package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Star;
import com.example.hope.model.vo.StarVO;

public interface StarService extends BaseService<Star> {

    boolean insert(Star star,String token);

    boolean delete(Star star, String token);

    boolean isStarByStoreId(String token, long storeId);

    boolean isStarByProductId(String token, long productId);

    IPage<StarVO> findByStore(String token, Query query);

    IPage<StarVO> findByProduct(String token, Query query);
}
