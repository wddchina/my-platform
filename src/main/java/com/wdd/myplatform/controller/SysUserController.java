package com.wdd.myplatform.controller;


import com.wdd.myplatform.aop.LogParams;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @LogParams
    public SysUser getById(@ApiParam(name = "id") @PathVariable(value = "id")String id){
        return sysUserService.getById(id);
    }

    @PostMapping("/saveUser")
    @ApiOperation("保存用户")
    @LogParams
    public SysUser saveUser(@RequestBody SysUser sysUser){
        sysUserService.saveOrUpdate(sysUser);
        return sysUser;
    }

}

