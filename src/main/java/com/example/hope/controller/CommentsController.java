package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.Comments;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.CommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
@RequestMapping("/comments")
@Api(tags = "评论相关接口")
public class CommentsController {

    private CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @LoginUser
    @ApiOperation("添加评论")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Comments comments, HttpServletRequest request) {
        commentsService.insert(request.getHeader("Authorization"), comments);
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("删除评论")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(Comments comments, HttpServletRequest request) {
        commentsService.delete(comments, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    @LoginUser
    @ApiOperation("修改评论")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Comments comments, HttpServletRequest request) {
        commentsService.update(comments, request.getHeader("Authorization"));
        return ReturnMessageUtil.sucess();
    }

    @Admin
    @ApiOperation("查询全部评论")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(commentsService.findAll(option));
    }

    @LoginUser
    @ApiOperation("按商店id查询全部评论")
    @RequestMapping(value = "/findByStoreId/{storeId}", method = RequestMethod.GET)
    public ReturnMessage<Object> findByStoreId(@PathVariable long storeId, @RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(commentsService.findByStoreId(storeId, option));
    }
}
