package com.example.hope.controller;

import com.example.hope.model.entity.Product;
import com.example.hope.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "产品相关接口")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @ApiOperation("添加产品")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(Product product){
        productService.insert(product);
    }

    @ApiOperation("删除产品")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id){
        productService.delete(id);
    }

    @ApiOperation("修改产品")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void updateProduct(Product product){
        productService.update(product);
    }

    @ApiOperation("按类型查找全部产品")
    @RequestMapping(value = "/findAllByType/{serviceId}",method = RequestMethod.GET)
    public List<Product> findAllByType(@PathVariable("serviceId") long serviceId){
        return productService.findAllByType(serviceId);
    }

    @ApiOperation("查找全部产品")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<Product> findAll(){
        return productService.findAll();
    }
}