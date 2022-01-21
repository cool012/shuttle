package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ApproveProduct;
import com.example.hope.model.entity.ApproveStore;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.business.ApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 审批相关路由
 * @author: DHY
 * @created: 2021/05/02 16:43
 */
@RestController
@RequestMapping(value = "/approve")
@Api("审批相关路由")
public class ApproveController {

    @Resource
    private ApproveService approveService;

    @LoginUser
    @RequestMapping(value = "/insertStore", method = RequestMethod.POST)
    @ApiOperation("添加审批商店")
    public ReturnMessage<Object> insertStore(ApproveStore approveStore, HttpServletRequest request) {
        approveService.insertStore(approveStore, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    @ApiOperation("添加审批产品")
    public ReturnMessage<Object> insertProduct(ApproveProduct approveProduct, HttpServletRequest request) {
        approveService.insertProduct(approveProduct, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @Admin
    @RequestMapping(value = "/approveStore", method = RequestMethod.POST)
    @ApiOperation("批准商店")
    public ReturnMessage<Object> approveStore(ApproveStore approveStore) {
        approveService.approveStore(approveStore);
        return ReturnMessageUtil.success();
    }

    @Admin
    @RequestMapping(value = "/approveProduct", method = RequestMethod.POST)
    @ApiOperation("批准产品")
    public ReturnMessage<Object> approveProduct(ApproveProduct approveProduct) {
        approveService.approveProduct(approveProduct);
        return ReturnMessageUtil.success();
    }

    @Admin
    @RequestMapping(value = "/findAllStore", method = RequestMethod.GET)
    @ApiOperation("查询全部审批商店")
    public ReturnMessage<Object> findAllStore(Map<String, String> option) {
        return ReturnMessageUtil.success(approveService.findAllStore(option));
    }

    @Admin
    @RequestMapping(value = "/findAllProduct", method = RequestMethod.GET)
    @ApiOperation("查询全部审批产品")
    public ReturnMessage<Object> findAllProduct(Map<String, String> option) {
        return ReturnMessageUtil.success(approveService.findAllProduct(option));
    }

    @LoginUser
    @RequestMapping(value = "/findStoreByUserId/{userId}", method = RequestMethod.GET)
    @ApiOperation("查询当前用户拥有的商店")
    public ReturnMessage<Object> findStoreByUserId(@PathVariable long userId, HttpServletRequest request) {
        return ReturnMessageUtil.success(approveService.findStoreByUserId(userId, request.getHeader("Authorization")));
    }
}