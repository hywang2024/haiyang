package com.haiyang.proxy.hyproxy;

import java.lang.reflect.Method;

/**
 * @ClassName: HYInvocationHandler
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 11:09
 * @Version 1.0
 */
public interface HYInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
