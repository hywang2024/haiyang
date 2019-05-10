package com.haiyang.mybatis.v1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName: HYMapperProxy
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:22
 * @Version: 1.0
 */
public class HYMapperProxy implements InvocationHandler {

    private HYSqlSession sqlSession;

    public HYMapperProxy(HYSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;
        return sqlSession.selectOne(statementId, args[0]);
    }
}
