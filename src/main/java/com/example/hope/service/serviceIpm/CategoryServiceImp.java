package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.PageUtils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Category;
import com.example.hope.model.entity.Store;
import com.example.hope.model.mapper.CategoryMapper;
import com.example.hope.model.vo.CategoryVO;
import com.example.hope.service.business.CategoryService;
import com.example.hope.service.business.BusinessService;
import com.example.hope.service.business.StoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
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
                .in(Store::getCategoryId, categoryIds);
        return this.remove(this.getQueryWrapper(Category::getBusinessId, businessId))
                && storeService.removeByIds(storeService.list(wrapper));
    }

    /**
     * 更新类别
     *
     * @param category 类别
     */
    @Override
    @Transactional
    @CacheEvict(value = "category", allEntries = true)
    public boolean updateCategory(Category category) {
        BusinessException.check(!businessService.exist(category.getBusinessId()), "业务不存在");
        return this.updateById(category);
    }

    /**
     * 查询全部类别
     *
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #query.toString()")
    public IPage<CategoryVO> page(Query query) {
        IPage<Category> categoryPage = PageUtils.getQuery(query);
        return this.baseMapper.selectByPage(categoryPage);
    }

    /**
     * 根据 businessId 查询类别
     *
     * @param businessId 业务id
     * @return 类别列表
     */
    @Override
    @Cacheable(value = "category", key = "methodName + #businessId")
    public List<CategoryVO> findAllByServiceId(long businessId) {
        Wrapper<Category> wrapper = new QueryWrapper<Category>()
                .eq("category.business_id", businessId);
        return this.baseMapper.selectByList(wrapper);
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
    public CategoryVO detail(long id) {
        return this.baseMapper.detail(id);
    }
}