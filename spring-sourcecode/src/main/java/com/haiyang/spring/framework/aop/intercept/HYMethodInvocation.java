package com.haiyang.spring.framework.aop.intercept;

import com.haiyang.spring.framework.aop.aspect.HYJoinPoint;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HYMethodInvocation
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 15:23
 * @Version: 1.0
 */
public class HYMethodInvocation implements HYJoinPoint {

    private Object proxy;
    private Method method;
    private Object target;
    private Object[] arguments;
    private List<Object> interceptorsAndDynamicMethodMatchers;
    private Class<?> targetClass;
    private Map<String, Object> userAttributes;
    //定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;

    public HYMethodInvocation(Object proxy, Method method, Object target, Object[] arguments,
                              List<Object> interceptorsAndDynamicMethodMatchers, Class<?> targetClass) {
        this.proxy = proxy;
        this.method = method;
        this.target = target;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
        this.targetClass = targetClass;
    }

    public Object procced() throws Exception {
        //如果Interceptor执行完了，则执行joinPoint
        if (currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1) {
            this.method.invoke(this.target, this.getArguments());
        }
        Object interceptorOrInterceptionAdvice = interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        //如果要动态匹配joinPoint
        if (interceptorOrInterceptionAdvice instanceof HYMethodInterceptor) {
            HYMethodInterceptor interceptor = (HYMethodInterceptor) interceptorOrInterceptionAdvice;
            return interceptor.invoke(this);
        } else {
            ////动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
            return procced();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setUserAttribute(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            if (null == userAttributes) {
                userAttributes = new HashMap<>();
            }
            userAttributes.put(key, value);
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }
}
