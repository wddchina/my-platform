package com.wdd.myplatform.aop;

import java.lang.annotation.*;

/**
 * 有此注解则不进行token拦截
 * @author 19033717
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Inherited
public @interface DisableAuth {
}
