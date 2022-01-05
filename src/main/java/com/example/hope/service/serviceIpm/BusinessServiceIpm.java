package com.example.hope.service.serviceIpm;

import com.example.hope.base.service.imp.BaseServiceImp;
import com.example.hope.common.utils.Utils;
import com.example.hope.model.entity.Business;
import com.example.hope.model.mapper.BusinessMapper;
import com.example.hope.service.CategoryService;
import com.example.hope.service.BusinessService;
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
        // todo 删除成功判断
        categoryService.deleteByServiceId(id);
        return this.removeById(id);
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
    @Cacheable(value = "service", key = "methodName + #option.toString()")
    public PageInfo<Business> page(Map<String, String> option) {
        Utils.checkOption(option, Business.class);
        String orderBy = String.format("%s %s", option.get("sort"), option.get("order"));
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")), orderBy);
        return PageInfo.of(this.list());
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