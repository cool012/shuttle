package com.example.hope.controller;

import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.service.ServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
@Api(tags = "服务相关接口")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }

    @ApiOperation("添加服务")
    @RequestMapping(value = "/insert/{name}",method = RequestMethod.GET)
    public ReturnMessage<Object> insert(@PathVariable String name){
        serviceService.insert(name);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("删除服务")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(@PathVariable long id){
        serviceService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("查询全部服务")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(){
        return ReturnMessageUtil.sucess(serviceService.findAll());
    }
}