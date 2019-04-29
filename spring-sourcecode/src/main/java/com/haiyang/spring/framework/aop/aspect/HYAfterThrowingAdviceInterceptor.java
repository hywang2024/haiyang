package com.haiyang.spring.framework.aop.aspect;

import com.haiyang.spring.framework.aop.intercept.HYMethodInterceptor;
import com.haiyang.spring.framework.aop.intercept.HYMethodInvocation;

import java.lang.reflect.Method;

/**
 * @ClassName: HYAfterThrowingAdviceInterceptor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 18:24
 * @Version: 1.0
 */
public class HYAfterThrowingAdviceInterceptor extends HYAbstractAspectAdvice implements HYAdvice, HYMethodInterceptor {

    private String throwingName;

    public HYAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(HYMethodInvocation invocation) {
        try {
            invocation.procced();
        } catch (Exception e) {
            try {
                invokeAdviceMethod(invocation, null, e.getCause());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            e.printStackTrace();
        }
        return null;
    }

    public void setThrowingName(String throwingName) {
        this.throwingName = throwingName;
    }
}
