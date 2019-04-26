package com.haiyang.spring.framework.context.support;

/**
 * @ClassName: HYAbstractApplicationContext
 * @Description: IOC 容器实现
 * @Author: hywang
 * @Email: haiyang.wang01@mljr.com
 * @CreateDate: 2019-04-26 14:01
 * @Version: 1.0
 */
public abstract class HYAbstractApplicationContext {

    /**
     * 受保护的 只提供给子类实现
     * @throws Exception
     */
   public void refresh() throws Exception {

    }
}
