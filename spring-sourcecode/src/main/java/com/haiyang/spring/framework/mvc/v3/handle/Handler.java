package com.haiyang.spring.framework.mvc.v3.handle;

import com.haiyang.spring.framework.annection.HYRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @ClassName: Handler
 * @Description: 保存一个url和一个Method的关系
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/14 19:42
 * @Version 1.0
 */
public class Handler {
    private Pattern pattern;
    private Method method;
    private Object controller;
    private Class<?>[] paramTypes;

    //形参列表
    //参数的名字作为key,参数的顺序，位置作为值
    private Map<String, Integer> paramIndexMapping;

    public Handler(Pattern pattern, Method method, Object controller) {
        this.pattern = pattern;
        this.method = method;
        this.controller = controller;
        this.paramTypes = method.getParameterTypes();
        this.paramIndexMapping = new HashMap<>();
        putParamIndexMapping(method);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Method getMethod() {
        return method;
    }

    public Object getController() {
        return controller;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    private void putParamIndexMapping(Method method) {
        //提取方法中加了注解的参数
        //把方法上的注解拿到，得到的是一个二维数组
        //因为一个参数可以有多个注解，而一个方法又有多个参数
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof HYRequestParam) {
                    String paramName = ((HYRequestParam) annotation).value();
                    if (!"".equals(paramName)) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
        //提取方法中的request和response参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            if (clazz == HttpServletResponse.class || clazz == HttpServletRequest.class) {
                paramIndexMapping.put(clazz.getName(), i);
            }
        }
    }
}
