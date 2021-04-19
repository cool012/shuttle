package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
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
@RequestMapping("/major/poster")
@Api(tags = "广告相关接口")
public class AdsController {

    private AdsService adsService;

    @Autowired
    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    /**
     * showdoc
     * @catalog 广告
     * @title 添加广告
     * @description 添加广告的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /poster/insert
     * @param ads.image 必要 string 广告图片
     * @param ads.storeId 必要 long 广告商店id
     * @param expired 必要 int 过期时间(天)
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("添加广告")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Ads ads, int expired) {
        adsService.insert(ads, expired);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 广告
     * @title 删除广告
     * @description 删除广告的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /poster/delete
     * @param id 必要 long 广告id
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        adsService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 广告
     * @title 修改广告
     * @description 修改广告的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /poster/update
     * @param ads.image 必要 string 广告图片
     * @param ads.storeId 必要 long 广告商店id
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("修改广告")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Ads ads) {
        adsService.update(ads);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 广告
     * @title 查询全部广告
     * @description 查询全部广告的接口
     * @method het
     * @header Authorization 必选 String token
     * @url /poster/findAll
     * @return {"code": 1,"message": "success","data": "ads"}
     * @return_param  ads.image 必要 string 广告图片
     * @return_param  ads.storeId 必要 long 广告商店id
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("查询全部广告")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll() {
        return ReturnMessageUtil.sucess(adsService.findAll());
    }
}