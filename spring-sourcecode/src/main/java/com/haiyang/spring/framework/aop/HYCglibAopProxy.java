package com.haiyang.spring.framework.aop;

import com.haiyang.spring.framework.aop.support.HYAdvisedSupport;

/**
 * @ClassName: HYCglibAopProxy
 * @Description: cglib代理
 * @Author: hywang
 * @CreateDate: 2019-04-28 14:56
 * @Version: 1.0
 */
public class HYCglibAopProxy implements HYAopProxy{

    private HYAdvisedSupport support;

    public HYCglibAopProxy(HYAdvisedSupport support) {
        this.support = support;
    }


    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
