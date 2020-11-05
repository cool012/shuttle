package com.example.hope.service.serviceIpm;

import com.example.hope.model.entity.Category;
import com.example.hope.model.mapper.CategoryMapper;
import com.example.hope.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

/**
 * @description: 类别服务实现类
 * @author: DHY
 * @created: 2020/10/30 13:41
 */
@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImp(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public int delete(long id) {
        return categoryMapper.delete(id);
    }

    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    @Cacheable(value = "category", key = "methodName")
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    @Cacheable(value = "category", key = "methodName + #serviceId")
    public List<Category> findAllByServiceId(long serviceId) {
        return categoryMapper.findAllByServiceId(serviceId);
    }
}
