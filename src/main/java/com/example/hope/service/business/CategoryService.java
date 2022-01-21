package com.example.hope.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.BaseService;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Category;
import com.example.hope.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService extends BaseService<Category> {

    boolean insert(Category category);

    boolean delete(long id);

    boolean deleteByServiceId(long serviceId);

    boolean updateCategory(Category category);

    boolean exist(long id);

    IPage<CategoryVO> page(Query query);

    List<CategoryVO> findAllByServiceId(long serviceId);

    CategoryVO detail(long id);
}
