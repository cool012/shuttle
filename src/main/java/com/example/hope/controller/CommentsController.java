package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description: 评论相关路由
 * @author: DHY
 * @created: 2021/02/17 13:57
 */
@RestController
@RequestMapping("/major/comments")
@Api(tags = "评论相关接口")
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * showdoc
     *
     * @param comments.content 必选 string 评论内容
     * @param comments.name    必选 string 评论用户昵称
     * @param comments.storeId 必选 long 评论商店id
     * @param comments.date    必选 string 评论时间
     * @param comments.userId  必选 long 评论用户id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 评论
     * @title 添加评论
     * @description 添加评论的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /comments/insert
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("添加评论")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Comments comments, HttpServletRequest request) {
        commentsService.insert(request.getHeader("Authorization"), comments);
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param comments.content 必选 string 评论内容
     * @param comments.name    必选 string 评论用户昵称
     * @param comments.storeId 必选 long 评论商店id
     * @param comments.date    必选 string 评论时间
     * @param comments.userId  必选 long 评论用户id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 评论
     * @title 删除评论
     * @description 删除评论的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /comments/delete
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("删除评论")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(Comments comments, HttpServletRequest request) {
        commentsService.delete(comments, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param comments.content 必选 string 评论内容
     * @param comments.name    必选 string 评论用户昵称
     * @param comments.storeId 必选 long 评论商店id
     * @param comments.date    必选 string 评论时间
     * @param comments.userId  必选 long 评论用户id
     * @return {"code": 1,"message": "success","data": "null"}
     * @catalog 评论
     * @title 修改评论
     * @description 修改评论的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /comments/update
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("修改评论")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Comments comments, HttpServletRequest request) {
        commentsService.update(comments, request.getHeader("Authorization"));
        return ReturnMessageUtil.success();
    }

    /**
     * showdoc
     *
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "comments"}
     * @catalog 评论
     * @title 查询全部评论
     * @description 查询全部评论的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /comments/findAll
     * @return_param comments.content 必选 string 评论内容
     * @return_param comments.name 必选 string 评论用户昵称
     * @return_param comments.storeId 必选 long 评论商店id
     * @return_param comments.date 必选 string 评论时间
     * @return_param comments.userId 必选 long 评论用户id
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("查询全部评论")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(commentsService.findAll(option));
    }

    /**
     * showdoc
     *
     * @param storeId  必选 long 商店id
     * @param pageNo   可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort     可选 string 排序
     * @param order    可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "comments"}
     * @catalog 评论
     * @title 按商店id查询全部评论
     * @description 按商店id查询全部评论的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /comments/findByStoreId/{storeId}
     * @return_param comments.content 必选 string 评论内容
     * @return_param comments.name 必选 string 评论用户昵称
     * @return_param comments.storeId 必选 long 评论商店id
     * @return_param comments.date 必选 string 评论时间
     * @return_param comments.userId 必选 long 评论用户id
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("按商店id查询全部评论")
    @RequestMapping(value = "/findByStoreId/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable long storeId, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.success(commentsService.findByStoreId(storeId, option));
    }

    @RequestMapping(value = "/updateByUserId", method = RequestMethod.POST)
    public ReturnMessage<Object> updateByUserId(long userId, String newName) {
        commentsService.updateByUserId(userId, newName);
        return ReturnMessageUtil.success();
    }
}