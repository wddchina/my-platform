package com.wdd.myplatform.controller;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author wdd
 * @since 2020-12-22
 */
@RestController
@RequestMapping("/sysUser")
@Api("sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询用户")
    public SysUser getById(@ApiParam(name = "id") @PathVariable(value = "id")String id){
        return sysUserService.getById(id);
    }

}

