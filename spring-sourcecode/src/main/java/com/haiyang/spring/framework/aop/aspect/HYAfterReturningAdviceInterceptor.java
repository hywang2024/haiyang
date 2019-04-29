package com.haiyang.spring.framework.aop.aspect;

import com.haiyang.spring.framework.aop.intercept.HYMethodInterceptor;
import com.haiyang.spring.framework.aop.intercept.HYMethodInvocation;

import java.lang.reflect.Method;

/**
 * @ClassName: HYAfterReturningAdviceInterceptor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 18:15
 * @Version: 1.0
 */
public class HYAfterReturningAdviceInterceptor extends HYAbstractAspectAdvice implements HYAdvice, HYMethodInterceptor {

    private HYJoinPoint joinPoint;

    public HYAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(HYMethodInvocation invocation) {
        Object result = null;
        try {
            result = invocation.procced();
            this.joinPoint = invocation;
            afterReturning(result, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint, retVal, null);
    }
}
