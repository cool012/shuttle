package com.example.hope.controller;

import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.model.entity.Store;
import com.example.hope.service.StoreService;
import com.example.hope.service.serviceIpm.StoreServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 商店相关路由
 * @author: DHY
 * @created: 2021/02/03 19:56
 */
@RestController
@RequestMapping("/store")
@Api(tags = "商店相关接口")
public class StoreController {

    private StoreService storeService;

    @Autowired
    public StoreController(StoreServiceImp storeService) {
        this.storeService = storeService;
    }

    @ApiOperation("添加商店")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Store store) {
        storeService.insert(store);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("删除商店")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        storeService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("更新商店")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Store store) {
        storeService.update(store);
        return ReturnMessageUtil.sucess();
    }

    @ApiOperation("查询所有商店")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll() {
        return ReturnMessageUtil.sucess(storeService.findAll());
    }

    @ApiOperation("根据serviceId查询商店")
    @RequestMapping(value = "/findByServiceId/{serviceId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByServiceId(@PathVariable("serviceId") long serviceId) {
        return ReturnMessageUtil.sucess(storeService.findByServiceId(serviceId));
    }

    @ApiOperation("根据categoryId查询商店")
    @RequestMapping(value = "/findByCategoryId/{categoryId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCategoryId(@PathVariable("categoryId") long categoryId) {
        return ReturnMessageUtil.sucess(storeService.findByCategoryId(categoryId));
    }
}
