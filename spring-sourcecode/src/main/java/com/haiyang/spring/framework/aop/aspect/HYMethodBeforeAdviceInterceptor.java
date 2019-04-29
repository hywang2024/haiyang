package com.haiyang.spring.framework.aop.aspect;

import com.haiyang.spring.framework.aop.intercept.HYMethodInterceptor;
import com.haiyang.spring.framework.aop.intercept.HYMethodInvocation;

import java.lang.reflect.Method;

/**
 * @ClassName: HYMethodBeforeAdviceInterceptor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 17:57
 * @Version: 1.0
 */
public class HYMethodBeforeAdviceInterceptor extends HYAbstractAspectAdvice implements HYAdvice, HYMethodInterceptor {

    private HYJoinPoint joinPoint;

    public HYMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(HYMethodInvocation invocation) {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = invocation;
        Object result = null;
        try {
            before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
            result = invocation.procced();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private void before(Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint, null, null);
    }
}