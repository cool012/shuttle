package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Category;
import com.example.hope.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description: 类别相关路由
 * @author: DHY
 * @created: 2020/10/30 13:49
 */
@RestController
@RequestMapping("/category")
@Api(tags = "类别相关接口")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    /**
     * showdoc
     * @catalog 类别
     * @title 添加
     * @description 添加类别的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /service/insert
     * @param category.name 必选 string 类别名称
     * @param category.serviceId 必选 string 类别图标
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("添加类别")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Category category) {
        categoryService.insert(category);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 类别
     * @title 删除
     * @description 删除类别的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /service/delete
     * @param id 必选 long 类别id
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("删除类别")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        categoryService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 类别
     * @title 修改
     * @description 修改类别的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /service/update
     * @param category.name 必选 string 类别名称
     * @param category.serviceId 必选 string 类别图标
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("修改类别")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Category category) {
        categoryService.update(category);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 类别
     * @title 查询全部
     * @description 查询全部类别的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /service/findAll
     * @param pageNo 可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort 可选 string 排序
     * @param order 可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "categories"}
     * @return_param category.name string 类别名称
     * @return_param category.serviceId string 类别图标
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("查询全部类别")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Map<String, String> option) {
        return ReturnMessageUtil.sucess(categoryService.findAll(option));
    }

    /**
     * showdoc
     * @catalog 类别
     * @title 按服务id查询
     * @description 按服务id查询类别的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /service/findAllByServiceId/{id}
     * @param id 必选 long 类别id
     * @return {"code": 1,"message": "success","data": "category"}
     * @return_param category.name string 类别名称
     * @return_param category.serviceId string 类别图标
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("按服务id查询全部类别")
    @RequestMapping(value = "/findAllByServiceId/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findAllByServiceId(@PathVariable long id) {
        return ReturnMessageUtil.sucess(categoryService.findAllByServiceId(id));
    }
}