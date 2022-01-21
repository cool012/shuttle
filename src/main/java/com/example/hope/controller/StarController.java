package com.example.hope.controller;

import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Star;
import com.example.hope.service.business.StarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 收藏相关路由
 * @author: DHY
 * @created: 2021/04/27 14:36
 */

@RestController
@RequestMapping("/major/star")
@Api("收藏相关接口")
public class StarController {

    @Resource
    private StarService starService;

    @LoginUser
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation("添加收藏")
    public ReturnMessage<Object> insert(Star star, HttpServletRequest request) {
        starService.insert(star, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation("删除收藏")
    public ReturnMessage<Object> delete(Star star, HttpServletRequest request) {
        starService.delete(star, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    @LoginUser
    @RequestMapping(value = "/findByStore", method = RequestMethod.GET)
    @ApiOperation("查询用户的所有商店收藏")
    public ReturnMessage<Object> findByStore(HttpServletRequest request, Query query) {
        return ReturnMessageUtil.success(starService.findByStore(request.getHeader("Authorization"), query));
    }

    @LoginUser
    @RequestMapping(value = "/findByProduct", method = RequestMethod.GET)
    @ApiOperation("查询用户的所有产品收藏")
    public ReturnMessage<Object> findByProduct(HttpServletRequest request, Query query) {
        return ReturnMessageUtil.success(starService.findByProduct(request.getHeader("Authorization"), query));
    }

    @LoginUser
    @RequestMapping(value = "/isStarByStoreId/{storeId}", method = RequestMethod.GET)
    @ApiOperation("用户的商店是否被收藏")
    public ReturnMessage<Object> isStarByStoreId(@PathVariable long storeId, HttpServletRequest request) {
        return ReturnMessageUtil.success(starService.isStarByStoreId(request.getHeader("Authorization"), storeId));
    }

    @LoginUser
    @RequestMapping(value = "/isStarByProductId/{productId}", method = RequestMethod.GET)
    @ApiOperation("用户的产品是否被收藏")
    public ReturnMessage<Object> isStarByProductId(@PathVariable long productId, HttpServletRequest request) {
        return ReturnMessageUtil.success(starService.isStarByProductId(request.getHeader("Authorization"), productId));
    }
}