package com.haiyang.spring.framework.beans;

import lombok.Getter;

/**
 * @ClassName: HYBeanWrapper
 * @Description: 代理
 * @Author: hywang
 * @Email: haiyang.wang01@mljr.com
 * @CreateDate: 2019-04-26 11:55
 * @Version: 1.0
 */
@Getter
public class HYBeanWrapper {

    private Object wrapperInstance;
    private Class<?> wrapperClass;

    public HYBeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
    }


}
