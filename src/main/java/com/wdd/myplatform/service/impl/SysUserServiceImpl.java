package com.wdd.myplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.mapper.SysUserMapper;
import com.wdd.myplatform.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wdd
 * @since 2021-04-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * "根据登录名查询用户"
     * @param userName
     * @return
     */
    @Override
    public SysUser getByUserName(String userName) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(SysUser.USER_NAME,userName);
        wrapper.last("limit 1");
        SysUser sysUser = this.baseMapper.selectOne(wrapper);
        return sysUser;
    }
}
