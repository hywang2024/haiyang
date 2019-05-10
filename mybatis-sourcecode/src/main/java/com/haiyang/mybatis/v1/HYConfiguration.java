package com.haiyang.mybatis.v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * @ClassName: HYConfig
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:17
 * @Version: 1.0
 */
public class HYConfiguration {

    public static final ResourceBundle sqlMappings;

    static {
        sqlMappings = ResourceBundle.getBundle("v1sql");
    }
    public <T> T getMapper(Class clazz, HYSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},
                new HYMapperProxy(sqlSession));
    }
}
