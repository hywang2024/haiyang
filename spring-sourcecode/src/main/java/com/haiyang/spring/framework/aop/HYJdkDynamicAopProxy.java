package com.haiyang.spring.framework.aop;

import com.haiyang.spring.framework.aop.intercept.HYMethodInvocation;
import com.haiyang.spring.framework.aop.support.HYAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @ClassName: HYJdkDynamicAopProxy
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 14:59
 * @Version: 1.0
 */
public class HYJdkDynamicAopProxy implements HYAopProxy, InvocationHandler {
    private HYAdvisedSupport support;

    public HYJdkDynamicAopProxy(HYAdvisedSupport support) {
        this.support = support;
    }

    @Override
    public Object getProxy() {
        return getProxy(support.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, support.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicInterceptionAdvice =
                support.getInterceptorsAndDynamicInterceptionAdvice(method, support.getTargetClass());
        HYMethodInvocation methodInvocation =
                new HYMethodInvocation(proxy, method, support.getTarget(), args, interceptorsAndDynamicInterceptionAdvice, support.getTargetClass());
        return methodInvocation.procced();
    }
}
