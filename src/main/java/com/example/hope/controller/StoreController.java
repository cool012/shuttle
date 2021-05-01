package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Store;
import com.example.hope.service.StoreService;
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

    /**
     * showdoc
     *
     * @param store.name       必选 string 商店名称
     * @param store.serviceId  必选 long 商店服务id
     * @param store.categoryId 必选 long 商店类别id
     * @param store.image      必选 string 商店图片
     * @param store.rate       必选 int 商店评分
     * @param store.sales      必选 int 商店销量
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 商店
     * @title 添加
     * @description 添加商店的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /store/insert
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("添加商店")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Store store, HttpServletRequest request) {
        storeService.insert(store, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param id 必选 long 商店id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 商店
     * @title 删除
     * @description 删除商店的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /store/delete
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("删除商店")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        storeService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param store.name       必选 string 商店名称
     * @param store.serviceId  必选 long 商店服务id
     * @param store.categoryId 必选 long 商店类别id
     * @param store.image      必选 string 商店图片
     * @param store.rate       必选 int 商店评分
     * @param store.sales      必选 int 商店销量
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 商店
     * @title 更新
     * @description 更新商店的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /store/update
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("更新商店")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Store store) {
        storeService.update(store);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "stores"}
     * @catalog 商店
     * @title 查询所有商店
     * @description 查询所有商店的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/findAll
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("查询所有商店")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(storeService.findAll(option));
    }

    /**
     * showdoc
     *
     * @param pageNo    可选 int 页数
     * @param pageSize  可选 int 每页数据条数
     * @param sort      可选 string 排序
     * @param order     可选 string 顺序(ASC/DESC)
     * @param serviceId 必选 long 商店id
     * @return {"code": 1,"message": "success","data": "stores"}
     * @catalog 商店
     * @title 根据serviceId查询商店
     * @description 根据serviceId查询商店的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/findByServiceId/{serviceId}
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("根据serviceId查询商店")
    @RequestMapping(value = "/findByServiceId/{serviceId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByServiceId(@PathVariable("serviceId") long serviceId,
                                                 @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(storeService.findByServiceId(serviceId, option));
    }

    /**
     * showdoc
     *
     * @param pageNo     可选 int 页数
     * @param pageSize   可选 int 每页数据条数
     * @param sort       可选 string 排序
     * @param order      可选 string 顺序(ASC/DESC)
     * @param categoryId 必选 long 类别id
     * @return {"code": 1,"message": "success","data": "stores"}
     * @catalog 商店
     * @title 根据categoryId查询商店
     * @description 根据categoryId查询商店的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/findByCategoryId/{categoryId}
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("根据categoryId查询商店")
    @RequestMapping(value = "/findByCategoryId/{categoryId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByCategoryId(@PathVariable("categoryId") long categoryId,
                                                  @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(storeService.findByCategoryId(categoryId, option));
    }

    /**
     * showdoc
     *
     * @return {"code": 1,"message": "success","data": "store"}
     * @catalog 商店
     * @title 根据id查询商店
     * @description 根据id查询商店的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/findById/{id}
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("根据id查询商店")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.sucess(storeService.findById(id));
    }

    /**
     * showdoc
     *
     * @return {"code": 1,"message": "success","data": "store"}
     * @catalog 商店
     * @title 排行榜
     * @description 商店排行榜的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/rank
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("排行榜")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public ReturnMessage<Object> rank(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(storeService.rank(option));
    }

    /**
     * showdoc
     *
     * @param keyword  必选 string 关键词
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "stores"}
     * @catalog 商店
     * @title 搜索商店
     * @description 搜索商店的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /store/search/{keyword}
     * @return_param store.name 必选 string 商店名称
     * @return_param store.serviceId 必选 long 商店服务id
     * @return_param store.categoryId 必选 long 商店类别id
     * @return_param store.image 必选 string 商店图片
     * @return_param store.rate 必选 int 商店评分
     * @return_param store.sales 必选 int 商店销量
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(storeService.search(keyword, option));
    }

    @LoginUser
    @ApiOperation("评分")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ReturnMessage<Object> review(long id, float rate, HttpServletRequest request) {
        storeService.review(id, rate, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }
}