package com.haiyang.spring.framework.aop;

/**
 * @ClassName: HYAopProxy
 * @Description: aop代理，默认使用JDK
 * @Author: hywang
 * @CreateDate: 2019-04-28 14:51
 * @Version: 1.0
 */
public interface HYAopProxy {
    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
