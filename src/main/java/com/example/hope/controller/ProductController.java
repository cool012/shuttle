package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.bo.Query;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.business.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/major/product")
@Api(tags = "产品相关接口")
public class ProductController {

    @Resource
    private ProductService productService;

    @Admin
    @ApiOperation("添加产品")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Product product) {
        productService.insert(product);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("删除产品")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        productService.delete(id);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("修改产品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> updateProduct(Product product) {
        productService.update(product);
        return ReturnMessageUtil.success();
    }

    @Admin
    @ApiOperation("查找全部产品")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(Query query) {
        return ReturnMessageUtil.success(productService.page(query));
    }

    @LoginUser
    @ApiOperation("根据storeId查找产品")
    @RequestMapping(value = "/findByStoreId/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable("storeId") long storeId) {
        return ReturnMessageUtil.success(productService.findByStoreId(storeId));
    }

    @LoginUser
    @ApiOperation("根据storeId查找产品（分页）")
    @RequestMapping(value = "/findByStoreIdByPagination/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(Query query, @PathVariable("storeId") long storeId) {
        return ReturnMessageUtil.success(productService.findByStoreId(storeId, query));
    }

    @Admin
    @ApiOperation("根据id查找产品")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.success(productService.findById(id));
    }

    @LoginUser
    @ApiOperation("更新产品评分")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ReturnMessage<Object> review(Product product, long orderId, HttpServletRequest request) {
        return ReturnMessageUtil.status(productService.review(product, request.getHeader("Authorization"), orderId));
    }

    @LoginUser
    @ApiOperation("排行榜")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public ReturnMessage<Object> rank(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.rank(option));
    }

    @LoginUser
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.search(keyword, option));
    }
}