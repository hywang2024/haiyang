package com.haiyang.mybatis.v2.binding;

import com.haiyang.mybatis.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * @ClassName: MapperProxyFactory
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-13 09:39
 * @Version: 1.0
 */
public class MapperProxyFactory<T> {
    private Class<T> mapperInterface;
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    public T newInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, new MapperProxy(sqlSession, object));
    }
}
