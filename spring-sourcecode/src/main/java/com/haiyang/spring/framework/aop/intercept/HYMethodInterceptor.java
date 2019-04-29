package com.haiyang.spring.framework.aop.intercept;

/**
 * @ClassName: HYMethodInterceptor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 15:54
 * @Version: 1.0
 */
public interface HYMethodInterceptor {
    Object invoke(HYMethodInvocation invocation);
}
