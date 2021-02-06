package com.example.hope.service.serviceIpm;

import com.example.hope.common.utils.Utils;
import com.example.hope.config.exception.BusinessException;
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
import java.util.List;
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
     * @param service
     */
    @Override
    @Transient
    @CacheEvict(value = "service",allEntries = true)
    public void insert(com.example.hope.model.entity.Service service){
        int res = serviceMapper.insert(service);
        log.info("service insert service -> " + service.toString() + " -> res -> " + res);
        BusinessException.check(res,"添加失败");
    }

    /**
     * 删除服务
     *
     * @param id
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
     * @param service
     */
    @Override
    @Transient
    @CacheEvict(value = "service",allEntries = true)
    public void update(com.example.hope.model.entity.Service service){
        int res = serviceMapper.update(service);
        log.info("service update service -> " + service.toString() + " -> res -> " + res);
        BusinessException.check(res,"修改失败");
    }

    /**
     * 查询全部服务
     * @return
     */
    @Override
    @Cacheable(value = "service",key = "methodName + #option.toString()")
    public PageInfo<com.example.hope.model.entity.Service> findAll(Map<String, String> option){
        Utils.check_map(option);
        PageHelper.startPage(Integer.valueOf(option.get("pageNo")), Integer.valueOf(option.get("pageSize")));
        return PageInfo.of(serviceMapper.findAll());
    }
}