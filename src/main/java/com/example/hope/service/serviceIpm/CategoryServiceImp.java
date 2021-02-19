package com.example.hope.service.serviceIpm;

import com.example.hope.common.logger.LoggerHelper;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Category;
import com.example.hope.model.mapper.CategoryMapper;
import com.example.hope.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 类别服务实现类
 * @author: DHY
 * @created: 2020/10/30 13:41
 */
@Log4j2
@Service
public class CategoryServiceImp implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private StoreServiceImp storeServiceImp;

    /**
     * 添加类别
     *
     * @param category 类别
     */
    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void insert(Category category) {
        int res = categoryMapper.insert(category);
        log.info(LoggerHelper.logger(category, res));
        BusinessException.check(res, "添加失败");
    }

    /**
     * 删除类别
     *
     * @param id 类别id
     */
    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void delete(long id) {
        int res = categoryMapper.delete(id, "id");
        storeServiceImp.deleteByCategoryId(id);
        log.info(LoggerHelper.logger(id, res));
        BusinessException.check(res, "删除失败");
    }

    /**
     * 根据服务id删除类别
     *
     * @param serviceId 服务id
     */
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void deleteByServiceId(long serviceId) {
        for (Category category : findAllByServiceId(serviceId)) storeServiceImp.deleteByCategoryId(category.getId());
        int res = categoryMapper.delete(serviceId, "serviceId");
        log.info(LoggerHelper.logger(serviceId, res));
    }

    /**
     * 更新类别
     *
     * @param category 类别
     */
    @Override
    @Transient
    @CacheEvict(value = "category", allEntries = true)
    public void update(Category category) {
        int res = categoryMapper.update(category);
        log.info(LoggerHelper.logger(category, res));
        BusinessException.check(res, "更新失败");
    }

    /**
     * 查询全部类别
     *
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName")
    public List<Category> findAll() {
        return categoryMapper.select(null, null);
    }

    /**
     * 根据serviceId查询类别
     *
     * @param serviceId 服务id
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #serviceId")
    public List<Category> findAllByServiceId(long serviceId) {
        return categoryMapper.select(String.valueOf(serviceId), "serviceId");
    }
}
