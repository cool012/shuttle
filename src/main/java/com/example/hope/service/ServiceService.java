package com.example.hope.service;

import com.example.hope.config.BusinessException;
import com.example.hope.model.mapper.ServiceMapper;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Log4j2
@Service
public class ServiceService {

    private ServiceMapper serviceMapper;
    
    @Autowired
    public ServiceService(ServiceMapper serviceMapper){
        this.serviceMapper = serviceMapper;
    }

    /**
     * 添加服务
     * @param serviceName
     */
    public void insert(String serviceName){
        int res = serviceMapper.insert(serviceName);
        log.info("service insert serviceName -> " + serviceName + " -> res -> " + res);
        BusinessException.isExist(res,"添加失败");
    }

    /**
     * 删除服务
     * @param id
     */
    public void delete(Long id) {
        int res = serviceMapper.delete(id);
        log.info("service delete id -> " + id + " -> res -> " + res);
        BusinessException.isExist(res,"删除失败");
    }

    /**
     * 查询全部服务
     * @return
     */
    public List<com.example.hope.model.entity.Service> findAll(){
        List<com.example.hope.model.entity.Service> list = serviceMapper.findAll();
        log.info("findAll service -> " + list.toString());
        return list;
    }
}
