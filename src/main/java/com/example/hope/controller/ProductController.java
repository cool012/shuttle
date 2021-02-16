package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.ProductService;
import com.example.hope.service.serviceIpm.ProductServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Api(tags = "产品相关接口")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductServiceIpm productService) {
        this.productService = productService;
    }

    @Admin
    @ApiOperation("添加产品")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Product product) {
        productService.insert(product);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("删除产品")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        productService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("修改产品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> updateProduct(Product product) {
        productService.update(product);
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("查找全部产品")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(productService.findAll(option));
    }

    @LoginUser
    @ApiOperation("根据storeId查找产品")
    @RequestMapping(value = "/findByStoreId/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable("storeId") long storeId) {
        return ReturnMessageUtil.sucess(productService.findByStoreId(storeId));
    }

    @Admin
    @ApiOperation("根据id查找产品")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.sucess(productService.findById(id));
    }

    @LoginUser
    @ApiOperation("更新产品评分")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ReturnMessage<Object> review(long id, int rate, HttpServletRequest request) {
        productService.review(id, rate, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("排行榜")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public ReturnMessage<Object> rank() {
        return ReturnMessageUtil.sucess(productService.rank());
    }

    @LoginUser
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword) {
        return ReturnMessageUtil.sucess(productService.search(keyword));
    }
}