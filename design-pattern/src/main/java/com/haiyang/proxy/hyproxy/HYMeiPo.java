package com.haiyang.proxy.hyproxy;

import java.lang.reflect.Method;

/**
 * @ClassName: HYMeiPo
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 11:12
 * @Version 1.0
 */
public class HYMeiPo implements HYInvocationHandler {
    private Object target;

    public Object getInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        return HYProxy.newProxyInstance(new HYClassLoader(),clazz.getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target,args);
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
