package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
import com.example.hope.model.entity.Services;
import com.example.hope.model.mapper.ServiceMapper;
import com.example.hope.service.ServiceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Map;

@Log4j2
@Service
public class ServiceServiceIpm implements ServiceService {

    private ServiceMapper serviceMapper;
    
    @Autowired
    public ServiceServiceIpm(ServiceMapper serviceMapper){
        this.serviceMapper = serviceMapper;
    }

    /**
     * 添加服务
     * @param services 服务
     */
    @Override
    @Transient
    @CacheEvict(value = "service",allEntries = true)
    public void insert(Services services){
        int res = serviceMapper.insert(services);
        log.info("service insert service -> " + services.toString() + " -> res -> " + res);
        BusinessException.check(res,"添加失败");
    }

    /**
     * 删除服务
     *
     * @param id 服务id
     */
    @Override
    @Transient
    @CacheEvict(value = "service",allEntries = true)
    public void delete(Long id) {
        int res = serviceMapper.delete(id);
        log.info("service delete id -> " + id + " -> res -> " + res);
        BusinessException.check(res,"删除失败");
    }

    /**
     * 修改服务
     * @param services 服务
     */
    @Override
    @Transient
    @CacheEvict(value = "service",allEntries = true)
    public void update(Services services){
        int res = serviceMapper.update(services);
        log.info("service update service -> " + services.toString() + " -> res -> " + res);
        BusinessException.check(res,"修改失败");
    }

    /**
     * 查询全部服务
     * @return 分页包装类
     */
    @Override
    @Cacheable(value = "service",key = "methodName + #option.toString()")
    public PageInfo<Services> findAll(Map<String, String> option){
        Utils.check_map(option);
        PageHelper.startPage(Integer.parseInt(option.get("pageNo")), Integer.parseInt(option.get("pageSize")));
        return PageInfo.of(serviceMapper.findAll());
    }
}