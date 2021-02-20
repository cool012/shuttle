package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Ads;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.AdsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 广告相关路由
 * @author: DHY
 * @created: 2020/10/30 13:49
 */
@RestController
@RequestMapping("/picture")
@Api(tags = "广告相关接口")
public class AdsController {

    private AdsService adsService;

    @Autowired
    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @Admin
    @ApiOperation("添加广告")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Ads ads, int expired) {
        adsService.insert(ads, expired);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        adsService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("修改广告")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Ads ads) {
        adsService.update(ads);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("查询全部广告")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll() {
        return ReturnMessageUtil.sucess(adsService.findAll());
    }
}