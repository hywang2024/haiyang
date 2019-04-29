package com.haiyang.spring.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @ClassName: HYAbstractAspectAdvice
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 17:58
 * @Version: 1.0
 */
public class HYAbstractAspectAdvice implements HYAdvice {
    private Method aspectMethod;
    private Object aspectTarget;

    public HYAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }
    public Object invokeAdviceMethod(HYJoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable{
        Class<?> [] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object [] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i ++) {
                if(paramTypes[i] == HYJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }

}
