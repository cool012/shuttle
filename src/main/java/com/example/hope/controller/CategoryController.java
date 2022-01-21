package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Category;
import com.example.hope.service.business.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 类别相关路由
 * @author: DHY
 * @created: 2020/10/30 13:49
 */
@RestController
@RequestMapping("/major/category")
@Api(tags = "类别相关接口")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Admin
    @ApiOperation("添加类别")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Category category) {
        categoryService.insert(category);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("删除类别")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        categoryService.delete(id);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("修改类别")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Category category) {
        return ReturnMessageUtil.status(categoryService.updateCategory(category));
    }

    @Admin
    @ApiOperation("查询全部类别")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Query query) {
        return ReturnMessageUtil.success(categoryService.page(query));
    }

    @LoginUser
    @ApiOperation("按服务id查询全部类别")
    @RequestMapping(value = "/findAllByServiceId/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findAllByServiceId(@PathVariable long id) {
        return ReturnMessageUtil.success(categoryService.findAllByServiceId(id));
    }
}