package com.wdd.myplatform.aop;

import com.wdd.myplatform.enums.BusinessType;

import java.lang.annotation.*;


/**
 * @author wdd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface LogParams {
    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;
}
