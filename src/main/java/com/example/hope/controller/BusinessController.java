package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Business;
import com.example.hope.service.BusinessService;
import com.example.hope.service.serviceIpm.BusinessServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/major/service")
@Api(tags = "服务相关接口")
public class BusinessController {

    private BusinessService businessService;

    @Autowired
    public BusinessController(BusinessServiceIpm serviceService) {
        this.businessService = serviceService;
    }

    @Admin
    @ApiOperation("添加服务")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Business business) {
        businessService.insert(business);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("删除服务")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        businessService.delete(id);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("修改服务")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Business business) {
        businessService.update(business);
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("查询全部服务")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(businessService.page(option));
    }

    @LoginUser
    @ApiOperation("查询全部服务(列表)")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ReturnMessage<Object> list() {
        return ReturnMessageUtil.success(businessService.list());
    }
}