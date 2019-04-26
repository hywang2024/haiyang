package com.haiyang.spring.framework.beans.config;

/**
 * @ClassName: HYBeanPostProcessor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-26 15:34
 * @Version: 1.0
 */
public class HYBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
