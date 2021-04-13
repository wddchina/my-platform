package com.wdd.myplatform.controller;


import com.wdd.myplatform.aop.LogParams;
import com.wdd.myplatform.common.BaseResult;
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

    /*@Resource
    private CuratorFramework curatorFramework;*/

    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询用户")
    @LogParams
    public BaseResult<SysUser> getById(@ApiParam(name = "id") @PathVariable(value = "id")String id){
        SysUser sysUser = sysUserService.getById(id);
        return BaseResult.success(sysUser);
    }

    @GetMapping("/getByLoginName")
    @ApiOperation("根据登录名查询用户")
    @LogParams
    public BaseResult<SysUser> getByUserName(
            @ApiParam(name = "userName",required = true) @RequestParam(value = "userName",required = true) String userName){
        SysUser sysUser = sysUserService.getByUserName(userName);
        return BaseResult.success(sysUser);
    }

    @PostMapping("/saveUser")
    @ApiOperation("保存用户")
    @LogParams
    public BaseResult<SysUser> saveUser(@RequestBody SysUser sysUser){
        sysUserService.saveOrUpdate(sysUser);
        return BaseResult.success(sysUser);
        /*InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/lock/saveUser");
        try {
            interProcessMutex.acquire();
            sysUserService.saveOrUpdate(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                interProcessMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sysUser;*/
    }

}

