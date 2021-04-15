package com.wdd.myplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.myplatform.entity.SysRole;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.entity.SysUserRole;
import com.wdd.myplatform.mapper.SysRoleMapper;
import com.wdd.myplatform.mapper.SysUserMapper;
import com.wdd.myplatform.mapper.SysUserRoleMapper;
import com.wdd.myplatform.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

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

    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq(SysUserRole.USER_ID,userId);
        List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(wrapper);
        List<Long> roleIdList = sysUserRoleList.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        List<SysRole> sysRoleList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleIdList)){
            sysRoleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>().in(SysRole.ROLE_ID, roleIdList));
        }
        return sysRoleList;
    }
}
