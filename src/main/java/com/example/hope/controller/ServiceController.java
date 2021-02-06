package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.Service;
import com.example.hope.service.ServiceService;
import com.example.hope.service.serviceIpm.ServiceServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/service")
@Api(tags = "服务相关接口")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceServiceIpm serviceService) {
        this.serviceService = serviceService;
    }

    @Admin
    @ApiOperation("添加服务")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Service service) {
        serviceService.insert(service);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("删除服务")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        serviceService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("修改服务")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Service service) {
        serviceService.update(service);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("查询全部服务")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(serviceService.findAll(option));
    }
}