package com.haiyang.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: JDKMeiPo
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 10:51
 * @Version 1.0
 */
public class JDKMeiPo implements InvocationHandler {

    private Object obj;

    public Object getInstance(Object obj) {
        this.obj = obj;
        Class<?> aClass = obj.getClass();
        return Proxy.newProxyInstance(aClass.getClassLoader(),aClass.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.obj,args);
        after();
        return obj;
    }

    private void before(){
        System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求");
        System.out.println("开始物色");
    }

    private void after(){
        System.out.println("OK的话，准备办事");
    }
}
