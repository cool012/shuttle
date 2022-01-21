package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Store;
import com.example.hope.service.business.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 商店相关路由
 * @author: DHY
 * @created: 2021/02/03 19:56
 */
@RestController
@RequestMapping("/major/store")
@Api(tags = "商店相关接口")
public class StoreController {

    @Resource
    private StoreService storeService;

    @LoginUser
    @ApiOperation("添加商店")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Store store) {
        storeService.insert(store);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("删除商店")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        storeService.delete(id);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("更新商店")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Store store) {
        storeService.update(store);
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @ApiOperation("查询所有商店")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Query query) {
        return ReturnMessageUtil.success(storeService.page(query));
    }

    @LoginUser
    @ApiOperation("根据serviceId查询商店")
    @RequestMapping(value = "/findByServiceId/{serviceId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByServiceId(Query query, @PathVariable("serviceId") long serviceId) {
        return ReturnMessageUtil.success(storeService.findByServiceId(serviceId, query));
    }

    @LoginUser
    @ApiOperation("根据categoryId查询商店")
    @RequestMapping(value = "/findByCategoryId/{categoryId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCategoryId(Query query, @PathVariable("categoryId") long categoryId) {
        return ReturnMessageUtil.success(storeService.findByCategoryId(categoryId, query));
    }

    @LoginUser
    @ApiOperation("根据id查询商店")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.success(storeService.delete(id));
    }

    @LoginUser
    @ApiOperation("排行榜")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public ReturnMessage<Object> rank(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(storeService.rank(option));
    }

    @LoginUser
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(storeService.search(keyword, option));
    }

    @LoginUser
    @ApiOperation("评分")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ReturnMessage<Object> review(long id, float rate, HttpServletRequest request) {
        return ReturnMessageUtil.status(storeService.review(id, rate, request.getHeader("Authorization")));
    }
}