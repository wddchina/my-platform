package com.wdd.myplatform.service;

import com.wdd.myplatform.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wdd
 * @since 2021-04-13
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * "根据登录名查询用户"
     * @param userName
     * @return
     */
    SysUser getByUserName(String userName);
}
