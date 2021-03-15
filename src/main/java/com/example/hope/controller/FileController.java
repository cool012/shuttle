package com.example.hope.controller;

import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.service.FileService;
import com.example.hope.service.serviceIpm.FileServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @description: 文件控制器
 * @author: DHY
 * @created: 2021/02/08 17:37
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件相关接口")
public class FileController {

    private FileService fileService;

    @Autowired
    FileController(FileServiceImp fileService) {
        this.fileService = fileService;
    }

    /**
     * showdoc
     * @catalog 文件
     * @title 上传
     * @description 上传文件的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /file/upload
     * @param file 必要 file 文件
     * @return {"code": 1,"message": "success","data": "filePath"}
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ReturnMessage<Object> upload(MultipartFile file) {
        return ReturnMessageUtil.sucess(fileService.upload(file));
    }

    /**
     * showdoc
     * @catalog 文件
     * @title 下载
     * @description 下载文件的接口
     * @method get
     * @header Authorization 必选 String token
     * @url /download/{fileName}
     * @param fileName 必要 string 文件名
     * @return file
     * @remark 只允许用户操作
     */
    @ApiOperation("下载")
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> download(@PathVariable("fileName") String fileName) throws FileNotFoundException {
        return fileService.download(fileName);
    }
}
