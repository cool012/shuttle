package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Category;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.CategoryMapper;
import com.example.hope.service.CategoryService;
import com.example.hope.service.BusinessService;
import com.example.hope.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 类别服务实现类
 * @author: DHY
 * @created: 2020/10/30 13:41
 */
@Log4j2
@Service
public class CategoryServiceImp extends BaseServiceImp<Category, CategoryMapper> implements CategoryService {

    @Resource
    private StoreService storeService;

    @Resource
    private BusinessService businessService;

    /**
     * 添加类别
     *
     * @param category 类别
     */
    @Override
    @Transactional
    @CacheEvict(value = "category", allEntries = true)
    public boolean insert(Category category) {
        BusinessException.check(!businessService.exist(category.getBusinessId()), "业务不存在");
        return this.save(category);
    }

    /**
     * 删除类别
     *
     * @param id 类别id
     */
    @Override
    @Transactional
    @CacheEvict(value = "category", allEntries = true)
    public boolean delete(long id) {
        return this.removeById(id) && storeService.deleteByCategoryId(id);
    }

    /**
     * 根据业务id删除类别
     *
     * @param businessId 业务id
     */
    @Override
    @Transactional
    @CacheEvict(value = "category", allEntries = true)
    public boolean deleteByServiceId(long businessId) {
        List<Category> categoryList = this.list(this.getQueryWrapper(Category::getBusinessId, businessId));
        List<Long> categoryIds = categoryList.stream()
                .map(Category::getId).collect(Collectors.toList());
        Wrapper<Store> wrapper = new LambdaQueryWrapper<Store>()
                .in(Store::getCategory, categoryIds);
        return this.remove(this.getQueryWrapper(Category::getBusinessId, businessId))
                && storeService.removeBatchByIds(storeService.list(wrapper));
    }

    /**
     * 更新类别
     *
     * @param category 类别
     */
    @Override
    @Transactional
    @CacheEvict(value = "category", allEntries = true)
    public boolean update(Category category) {
        BusinessException.check(!businessService.exist(category.getBusinessId()), "业务不存在");
        return this.update(category);
    }

    /**
     * 查询全部类别
     *
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #option.toString()")
    public PageInfo<Category> page(Map<String, String> option) {
        Utils.checkOption(option, Category.class);
        String orderBy = String.format("category.%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(this.list());
    }

    /**
     * 根据 businessId 查询类别
     *
     * @param businessId 业务id
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #businessId")
    public List<Category> findAllByServiceId(long businessId) {
        return this.list(this.getQueryWrapper(Category::getBusinessId, businessId));
    }

    /**
     * 是否存在类别
     *
     * @param id 类别id
     * @return boolean
     */
    @Override
    public boolean exist(long id) {
        return detail(id) != null;
    }

    /**
     * 根据id查询类别
     *
     * @param id 类别id
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #id")
    public Category detail(long id) {
        return this.getById(id);
    }
}