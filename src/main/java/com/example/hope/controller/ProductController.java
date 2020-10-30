package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.User;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Product;
import com.example.hope.config.exception.ReturnMessage;
import com.example.hope.service.ProductService;
import com.example.hope.service.serviceIpm.ProductServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
@Api(tags = "产品相关接口")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductServiceIpm productService){
        this.productService = productService;
    }

//    @Admin
    @ApiOperation("添加产品")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Product product){
        productService.insert(product);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("删除产品")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(@PathVariable("id") long id){
        productService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("修改产品")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ReturnMessage<Object> updateProduct(Product product){
        productService.update(product);
        return ReturnMessageUtil.sucess();
    }

    @User
    @ApiOperation("按类型查找全部产品")
    @RequestMapping(value = "/findAllByType/{serviceId}",method = RequestMethod.GET)
    public ReturnMessage<Object> findAllByType(@PathVariable("serviceId") long serviceId,@RequestParam Map<String, String> option){
        return ReturnMessageUtil.sucess(productService.findAllByType(serviceId,option));
    }

    @User
    @ApiOperation("查找全部产品")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option){
        return ReturnMessageUtil.sucess(productService.findAll(option));
    }

    @User
    @ApiOperation("按类型、分类查找全部产品")
    @RequestMapping(value = "/findAllByTypeAndCategory/{serviceId}/{categoryId}",method = RequestMethod.GET)
    public ReturnMessage<Object> findAllByTypeAndCategory(@PathVariable long serviceId,@PathVariable long categoryId,@RequestParam Map<String, String> option){
        return ReturnMessageUtil.sucess(productService.findAllByTypeAndCategory(serviceId,categoryId,option));
    }
}