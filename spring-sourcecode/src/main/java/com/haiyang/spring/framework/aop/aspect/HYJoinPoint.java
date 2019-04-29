package com.haiyang.spring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @ClassName: HYJoinPoint
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 15:30
 * @Version: 1.0
 */
public interface HYJoinPoint {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key,String value);

    Object getUserAttribute(String key);
}
