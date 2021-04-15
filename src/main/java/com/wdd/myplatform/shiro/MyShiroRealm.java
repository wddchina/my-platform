package com.wdd.myplatform.shiro;

import com.wdd.myplatform.entity.SysRole;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.service.SysUserService;
import com.wdd.myplatform.utils.Constants;
import com.wdd.myplatform.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Description  : 身份校验核心类
 */

public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger _logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证)
     * Authentication 是用来验证用户身份
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth)
            throws AuthenticationException {

        _logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //通过username从数据库中查找 ManagerInfo对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = sysUserService.getByUserName(username);
        if (sysUser == null) {
            throw new AuthenticationException("用户不存在!");
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Constants.ACCESS_TOKEN).append(":");
        String key = stringBuffer.append(sysUser.getUserId()).toString();
        String redisToken = redisTemplate.opsForValue().get(key);
        //如果redis中存在token,说明token未过期,自动延长7天
        if(StringUtils.equals(redisToken,token)){
            redisTemplate.opsForValue().set(key,redisToken,Constants.POSTPONE_DAY, TimeUnit.DAYS);
        }else {
            throw new AuthenticationException("token已过期，请重新登录!");
        }
        if (!JWTUtil.verify(token, username, sysUser.getPassword())) {
            throw new AuthenticationException("Token认证失败");
        }

        //明文: 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                managerInfo, //用户名
//                managerInfo.getPassword(), //密码
//                getName()  //realm name
//        );
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        _logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        String username = JWTUtil.getUsername(principals.toString());
        // 下面的可以使用缓存提升速度
        SysUser sysUser = sysUserService.getByUserName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysRole> sysRoleLists = new ArrayList<>();
        if(sysUser != null && null != sysUser.getUserId()){
            sysRoleLists = sysUserService.listRolesByUserId(sysUser.getUserId());
        }
            //设置相应角色的权限信息
            for (SysRole role : sysRoleLists) {
                //设置角色
                authorizationInfo.addRole(role.getRoleName());
                String roleKey = role.getRoleKey();
                if(StringUtils.isNotBlank(roleKey)){
                    String[] split = StringUtils.split(roleKey, ",");
                    Set<String> set = new HashSet<String>(Arrays.asList(split));
                    authorizationInfo.addStringPermissions(set);
                }
                /*for (Permission p : role.getPermissions()) {
                    //设置权限
                    authorizationInfo.addStringPermission(p.getPermission());
                }*/
            }

        return authorizationInfo;
    }
}
