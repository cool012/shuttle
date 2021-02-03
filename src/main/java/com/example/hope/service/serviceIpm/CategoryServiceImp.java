package com.example.hope.service.serviceIpm;

import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Category;
import com.example.hope.model.mapper.CategoryMapper;
import com.example.hope.service.CategoryService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
    public void insert(Category category) {
        int res = categoryMapper.insert(category);
        log.info("category insert -> " + category.toString() + " -> res -> " + res);
        BusinessException.check(res,"添加失败");
    }

    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void delete(long id) {
        int res = categoryMapper.delete(id);
        log.info("category delete -> " + id + " -> res -> " + res);
        BusinessException.check(res,"删除失败");
    }

    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void update(Category category) {
        int res = categoryMapper.update(category);
        log.info("category delete -> " + category.toString() + " -> res -> " + res);
        BusinessException.check(res,"更新失败");
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
