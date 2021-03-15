package com.example.hope.controller;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.LoginUser;
import com.example.hope.common.utils.ReturnMessageUtil;
import com.example.hope.model.entity.ReturnMessage;
import com.example.hope.model.entity.Services;
import com.example.hope.service.ServiceService;
import com.example.hope.service.serviceIpm.ServiceServiceIpm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/service")
@Api(tags = "服务相关接口")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceServiceIpm serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * showdoc
     * @catalog 服务
     * @title 添加
     * @description 添加服务的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /service/insert
     * @param services.name 必选 string 服务名称
     * @param services.icon 必选 string 服务图标
     * @param services.color 必选 string 服务颜色
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("添加服务")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ReturnMessage<Object> insert(Services services) {
        serviceService.insert(services);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 服务
     * @title 删除
     * @description 删除服务的接口
     * @method delete
     * @header Authorization 必选 String token
     * @url /service/delete
     * @param id 必选 long 服务id
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("删除服务")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ReturnMessage<Object> delete(long id) {
        serviceService.delete(id);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 服务
     * @title 修改
     * @description 修改服务的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /service/update
     * @param services.name 必选 string 服务名称
     * @param services.icon 必选 string 服务图标
     * @param services.color 必选 string 服务颜色
     * @return {"code": 1,"message": "success","data": "null"}
     * @remark 只允许管理员操作
     */
    @Admin
    @ApiOperation("修改服务")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnMessage<Object> update(Services services) {
        serviceService.update(services);
        return ReturnMessageUtil.sucess();
    }

    /**
     * showdoc
     * @catalog 服务
     * @title 查询全部
     * @description 查询服务的接口
     * @method post
     * @header Authorization 必选 String token
     * @url /service/findAll
     * @param pageNo 可选 int 页数
     * @param pageSize 可选 int 每页数据条数
     * @param sort 可选 string 排序
     * @param order 可选 string 顺序(ASC/DESC)
     * @return {"code": 1,"message": "success","data": "services"}
     * @return_param services.name string 服务名称
     * @return_param services.icon string 服务图标
     * @return_param services.color string 服务颜色
     * @remark 只允许用户操作
     */
    @LoginUser
    @ApiOperation("查询全部服务")
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ReturnMessage<Object> findAll(@RequestParam Map<String, String> option) {
        return ReturnMessageUtil.sucess(serviceService.findAll(option));
    }
}