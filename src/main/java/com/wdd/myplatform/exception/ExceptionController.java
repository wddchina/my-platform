package com.wdd.myplatform.exception;

import com.wdd.myplatform.common.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

/**
 * @author 19033717
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    /***
     * 捕捉UnauthorizedException
     * @param e
     * @return
     */
    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public BaseResult<Object> handleException(UnauthorizedException e) {
        String expcetionString = ExceptionUtils.getStackTrace(e);
        HashMap<String,Object> errorData = new HashMap<>();
        errorData.put("exceptionMessage",e.getMessage());
        log.error("ServiceException error={} trace = {} ",e.getMessage(),expcetionString);
        return BaseResult.error(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),errorData);
    }

    /**捕获AuthenticationException异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseResult handleException(AuthenticationException e){
        String expcetionString = ExceptionUtils.getStackTrace(e);
        HashMap<String,Object> errorData = new HashMap<>();
        errorData.put("exceptionMessage",e.getMessage());
        log.error("ServiceException error={} trace = {} ",e.getMessage(),expcetionString);
        return BaseResult.error(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),errorData);
    }

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public BaseResult handle401(ShiroException e) {
        String expcetionString = ExceptionUtils.getStackTrace(e);
        HashMap<String,Object> errorData = new HashMap<>();
        errorData.put("exceptionMessage",e.getMessage());
        return BaseResult.error(500, "shiro的异常", errorData);
    }

    /***
     * 未知来源的错误处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        String expcetionString = ExceptionUtils.getStackTrace(e);
        log.error("Exception error={} trace = {} ",e.getMessage(),expcetionString);
        HashMap<String,Object> errorData = new HashMap<>();
        errorData.put("exceptionMessage",e.getMessage());
        errorData.put("trace",expcetionString);
        return BaseResult.error(500, "error", errorData);
    }

}
