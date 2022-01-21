package com.example.hope.service.serviceIpm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.PageUtils;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Business;
import com.example.hope.model.mapper.BusinessMapper;
import com.example.hope.service.business.CategoryService;
import com.example.hope.service.business.BusinessService;
import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@Service
public class BusinessServiceIpm extends BaseServiceImp<Business, BusinessMapper> implements BusinessService {

    @Resource
    private CategoryService categoryService;

    /**
     * 添加服务
     *
     * @param business 服务
     */
    @Override
    @Transactional
    @CacheEvict(value = "service", allEntries = true)
    public boolean insert(Business business) {
        return this.save(business);
    }

    /**
     * 删除服务
     *
     * @param id 服务id
     */
    @Override
    @Transactional
    @CacheEvict(value = "service", allEntries = true)
    public boolean delete(Long id) {
        return this.removeById(id) && categoryService.deleteByServiceId(id);
    }

    /**
     * 修改服务
     *
     * @param business 服务
     */
    @Override
    @Transactional
    @CacheEvict(value = "service", allEntries = true)
    public boolean update(Business business) {
        return this.updateById(business);
    }

    /**
     * 查询全部服务
     *
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "service", key = "methodName + #query.toString()")
    public IPage<Business> selectByPage(Query query) {
        IPage<Business> businessPage = PageUtils.getQuery(query);
        return this.page(businessPage);
    }

    /**
     * 是否存在服务
     *
     * @param id 服务id
     * @return boolean
     */
    @Override
    public boolean exist(long id) {
        return get(id) != null;
    }

    @Override
    public List<Business> getList() {
        return this.list();
    }

    /**
     * 根据服务id查询服务
     *
     * @param id 服务id
     * @return 服务列表
     */
    @Override
    @Cacheable(value = "service", key = "methodName + #id")
    public Business get(long id) {
        return this.getById(id);
    }
}