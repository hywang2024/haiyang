package com.haiyang.spring.framework.beans.config;

import lombok.Data;

/**
 * @ClassName: HYBeanDefinition
 * @Description: 保存配置文件中的信息（相当于保存在内存中的配置）
 * @Author: hywang
 * @Email: haiyang.wang01@mljr.com
 * @CreateDate: 2019-04-26 11:52
 * @Version: 1.0
 */
@Data
public class HYBeanDefinition {
    private String beanClassName;
    private String factoryBeanName;
    private Boolean lazyInit = false;


}
