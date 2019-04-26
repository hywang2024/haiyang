package com.haiyang.spring.framework.beans.support;

import com.haiyang.spring.framework.beans.config.HYBeanDefinition;
import com.haiyang.spring.framework.context.support.HYAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: HYDefaultListableBeanFactory
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-26 14:04
 * @Version: 1.0
 */
public class HYDefaultListableBeanFactory extends HYAbstractApplicationContext {

    protected final Map<String, HYBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

}
