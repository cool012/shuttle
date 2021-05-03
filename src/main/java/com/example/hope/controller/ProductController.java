package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Product;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.ProductService;
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

    /**
     * showdoc
     *
     * @param product.name    必选 string 商品名称
     * @param product.price   必选 int 商品价格
     * @param product.image   必选 string 商品图片
     * @param product.sales   必选 int 商品销量
     * @param product.rate    必选 int 商品评分
     * @param product.storeId 必选 long 商店id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 产品
     * @title 添加
     * @description 添加商店的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /product/insert
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("添加产品")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Product product) {
        productService.insert(product);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param id 必选 long 商品id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 产品
     * @title 删除
     * @description 删除商店的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /product/delete
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("删除产品")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        productService.delete(id);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param product.name    必选 string 商品名称
     * @param product.price   必选 int 商品价格
     * @param product.image   必选 string 商品图片
     * @param product.sales   必选 int 商品销量
     * @param product.rate    必选 int 商品评分
     * @param product.storeId 必选 long 商店id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 产品
     * @title 修改
     * @description 修改商店的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /product/update
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("修改产品")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> updateProduct(Product product) {
        productService.update(product);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "product"}
     * @catalog 产品
     * @title 查找全部
     * @description 查找全部产品的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /product/findAll
     * @return_param product.name 必选 string 商品名称
     * @return_param product.price 必选 int 商品价格
     * @return_param product.image 必选 string 商品图片
     * @return_param product.sales 必选 int 商品销量
     * @return_param product.rate 必选 int 商品评分
     * @return_param product.storeId 必选 long 商店id
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("查找全部产品")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.findAll(option));
    }

    /**
     * showdoc
     *
     * @param storeId  必选 long 商店id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "products"}
     * @catalog 产品
     * @title 根据storeId查找产品
     * @description 根据storeId查找产品的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /product/findByStoreId/{storeId}
     * @return_param product.name 必选 string 商品名称
     * @return_param product.price 必选 int 商品价格
     * @return_param product.image 必选 string 商品图片
     * @return_param product.sales 必选 int 商品销量
     * @return_param product.rate 必选 int 商品评分
     * @return_param product.storeId 必选 long 商店id
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("根据storeId查找产品")
    @RequestMapping(value = "/findByStoreId/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable("storeId") long storeId) {
        return ReturnMessageUtil.success(productService.findByStoreId(storeId));
    }

    @LoginUser
    @ApiOperation("根据storeId查找产品（分页）")
    @RequestMapping(value = "/findByStoreIdByPagination/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable("storeId") long storeId, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.findByStoreId(storeId, option));
    }

    /**
     * showdoc
     *
     * @param id       必选 long 产品id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "product"}
     * @catalog 产品
     * @title 根据id查找产品
     * @description 根据id查找产品的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /product/findById/{id}
     * @return_param product.name 必选 string 商品名称
     * @return_param product.price 必选 int 商品价格
     * @return_param product.image 必选 string 商品图片
     * @return_param product.sales 必选 int 商品销量
     * @return_param product.rate 必选 int 商品评分
     * @return_param product.storeId 必选 long 商店id
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("根据id查找产品")
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ReturnMessage<Object> findById(@PathVariable("id") long id) {
        return ReturnMessageUtil.success(productService.findById(id));
    }

    /**
     * showdoc
     *
     * @param product.name    必选 string 商品名称
     * @param product.price   必选 int 商品价格
     * @param product.image   必选 string 商品图片
     * @param product.sales   必选 int 商品销量
     * @param product.rate    必选 int 商品评分
     * @param product.storeId 必选 long 商店id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 产品
     * @title 更新产品评分
     * @description 更新产品评分的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /product/review
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("更新产品评分")
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ReturnMessage<Object> review(Product product, long orderId, HttpServletRequest request) {
        productService.review(product, request.getHeader("Authorization"), orderId);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @return {"code": 1,"message": "success","data": "product"}
     * @catalog 产品
     * @title 排行榜
     * @description 商店排行榜的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /product/rank
     * @return_param product.name 必选 string 商品名称
     * @return_param product.price 必选 int 商品价格
     * @return_param product.image 必选 string 商品图片
     * @return_param product.sales 必选 int 商品销量
     * @return_param product.rate 必选 int 商品评分
     * @return_param product.storeId 必选 long 商店id
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("排行榜")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public ReturnMessage<Object> rank(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.rank(option));
    }

    /**
     * showdoc
     *
     * @param keyword  必选 string 关键词
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "products"}
     * @catalog 产品
     * @title 搜索
     * @description 商店搜索的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /product/update
     * @return_param product.name 必选 string 商品名称
     * @return_param product.price 必选 int 商品价格
     * @return_param product.image 必选 string 商品图片
     * @return_param product.sales 必选 int 商品销量
     * @return_param product.rate 必选 int 商品评分
     * @return_param product.storeId 必选 long 商店id
     * @remark 只允许管理员操作
     */
    @LoginUser
    @ApiOperation("搜索")
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public ReturnMessage<Object> search(@PathVariable("keyword") String keyword, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(productService.search(keyword, option));
    }
}