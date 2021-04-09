package com.wdd.myplatform.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogParamsAspect {

    private final String POINT_CUT = "@annotation(com.wdd.myplatform.aop.LogParams)";

    @Pointcut(POINT_CUT)
    public void cutAspect(){
    }

    @Around("cutAspect()")
    public Object aroundCut(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 参数
        Object[] args = joinPoint.getArgs();
        if(ArrayUtils.isEmpty(args)){
            Object proceed = joinPoint.proceed();
            String toJSONString = JSONObject.toJSONString(proceed);
            //saveDB(toJSONString)
            return proceed;
        }
        String jsonString = "";
        if(args.length==1 && isPrimite(args[0].getClass())){
            jsonString = args[0].toString();
        }else {
            jsonString = JSON.toJSONString(args);
        }
        // TODO: 2021/4/9 异步保存数据库
        //saveDB(jsonString)
        // TODO: 2021/4/9 更新返回值
        Object proceed = joinPoint.proceed();
        String toJSONString = JSONObject.toJSONString(proceed);
        //updateDB(toJSONString)
        return proceed;
        /*Parameter[] parameters = methodSignature.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            //Java自带基本类型的参数（例如Integer、String）的处理方式
            if (isPrimite(parameter.getType())) {
                continue;
            }
            *//*
             * 没有标注@ValidParam注解，或者是HttpServletRequest、HttpServletResponse、HttpSession时，都不做处理
             *//*
            if (parameter.getType().isAssignableFrom(HttpServletRequest.class) || parameter.getType().isAssignableFrom(HttpSession.class) ||
                    parameter.getType().isAssignableFrom(HttpServletResponse.class)) {
                continue;
            }
            Class<?> paramClazz = parameter.getType();
            //获取类型所对应的参数对象，实际项目中Controller中的接口不会传两个相同的自定义类型的参数，所以此处直接使用findFirst()
            Object arg = Arrays.stream(args).filter(ar -> paramClazz.isAssignableFrom(ar.getClass())).findFirst().get();
            //得到参数的所有成员变量
            Field[] declaredFields = paramClazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName();
                Class<?> fieldType = field.getType();
                Object fieldValue = field.get(arg);
                //校验标有@NotNull注解的字段
                *//*NotNull notNull = field.getAnnotation(NotNull.class);
                if (notNull != null) {
                    Object fieldValue = field.get(arg);
                    if (fieldValue == null) {
                        throw new RuntimeException(field.getName() + notNull.msg());
                    }
                }*//*
            }
        }*/
    }
    /**
     * 判断是否为基本类型：包括String
     * @param clazz clazz
     * @return  true：是;     false：不是
     */
    private boolean isPrimite(Class<?> clazz){
        return clazz.isPrimitive() || clazz == String.class;
    }
}
