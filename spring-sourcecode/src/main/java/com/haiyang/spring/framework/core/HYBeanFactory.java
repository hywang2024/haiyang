package com.haiyang.spring.framework.core;

/**
 * @ClassName: HYBeanFactory
 * @Description: BeanFactory 单利工厂的顶层设计
 * @Author: hywang
 * @Email: haiyang.wang01@mljr.com
 * @CreateDate: 2019-04-26 11:45
 * @Version: 1.0
 */
public interface HYBeanFactory {
    /**
     * 根据beanName 从ioc容器中获取实例Bean
     *
     * @param beanName
     * @return
     * @throws Exception
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanClass) throws Exception;
}
