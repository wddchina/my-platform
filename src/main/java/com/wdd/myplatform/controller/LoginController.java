package com.wdd.myplatform.controller;

import com.wdd.myplatform.aop.DisableAuth;
import com.wdd.myplatform.common.BaseResult;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.service.SysUserService;
import com.wdd.myplatform.utils.Constants;
import com.wdd.myplatform.utils.JWTUtil;
import com.wdd.myplatform.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;


/**
 * 登录接口类
 */

@RequestMapping("/sys")
@Controller
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger _logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    @DisableAuth
    @ResponseBody
    public BaseResult<String> login(@RequestBody SysUser loginParam) {
        _logger.info("用户请求登录获取Token");
        String username = loginParam.getUserName();
        String password = loginParam.getPassword();
        SysUser user = sysUserService.getByUserName(username);
        //随机数盐
        String salt = user.getSalt();
        //原密码加密（通过username + salt作为盐）
        String encodedPassword = Md5Utils.encrypt(password + username + salt);
        if (StringUtils.equals(user.getPassword(),encodedPassword)) {
            String accessToken = JWTUtil.sign(username, encodedPassword);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Constants.ACCESS_TOKEN).append(":");
            stringBuffer.append(user.getId());
            stringRedisTemplate.opsForValue().set(stringBuffer.toString(),accessToken,30, TimeUnit.MINUTES);
            return BaseResult.success(accessToken);
        } else {
            return BaseResult.error("密码错误");
        }
    }

}
