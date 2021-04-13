package com.wdd.myplatform.config;

import com.wdd.myplatform.aop.DisableAuth;
import com.wdd.myplatform.common.BaseResult;
import com.wdd.myplatform.entity.SysUser;
import com.wdd.myplatform.service.SysUserService;
import com.wdd.myplatform.utils.JWTUtil;
import com.wdd.myplatform.utils.SendMsgUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class AuthorizeInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class clazz = handlerMethod.getBean().getClass();
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(DisableAuth.class) || clazz.isAnnotationPresent(DisableAuth.class)) {
            return true;
        }
        if(method.isAnnotationPresent(RequestMapping.class) || method.isAnnotationPresent(GetMapping.class) || method.isAnnotationPresent(PostMapping.class)) {
            String token = request.getHeader("token");
            String username = JWTUtil.getUsername(token);
            SysUser sysUser = sysUserService.getByUserName(username);
            if(null == sysUser){
                throw new RuntimeException("用户不存在，请重新登录");
            }
            // TODO: 2021/4/12 验证token
            boolean verify = JWTUtil.verify(token, username, sysUser.getPassword());
            if(!verify){
                SendMsgUtil.sendJsonMessage(response, BaseResult.error("unauthorized"));
                return false;
            }
            if(verify){
                request.setAttribute("token", token);
                request.setAttribute("UserId", sysUser.getUserId());
                return true;
            }
            /*if(!authenticate.isAuthorized(accessToken)) {
                SendMsgUtil.sendJsonMessage(response, BaseResult.error("unauthorized"));
                return false;
            }*/
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
