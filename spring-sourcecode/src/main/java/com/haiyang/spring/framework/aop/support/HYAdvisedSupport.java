package com.haiyang.spring.framework.aop.support;

import com.haiyang.spring.framework.aop.aspect.HYAfterReturningAdviceInterceptor;
import com.haiyang.spring.framework.aop.aspect.HYAfterThrowingAdviceInterceptor;
import com.haiyang.spring.framework.aop.aspect.HYMethodBeforeAdviceInterceptor;
import com.haiyang.spring.framework.aop.config.HYAopConfig;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: HYAdvisedSupport
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-28 14:55
 * @Version: 1.0
 */
public class HYAdvisedSupport {

    private Class<?> targetClass;
    private Object target;
    private Pattern pointCutClassPattern;
    private HYAopConfig config;

    private transient Map<Method, List<Object>> methodCache;

    public HYAdvisedSupport(HYAopConfig config) {
        this.config = config;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }


    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws Exception {
        List<Object> caches = methodCache.get(method);
        //缓存未命中，则进行下一步处理
        if (CollectionUtils.isEmpty(caches)) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            caches = methodCache.get(m);

            //底层逻辑，对代理方法进行一个兼容处理 注意这一看一下 问什么再存一下
            this.methodCache.put(m, caches);

        }
        return caches;
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    private void parse() {
        String pointCut = this.config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(
                pointCutForClassRegex.lastIndexOf(" ") + 1));

        try {
            methodCache = new HashMap<>();
            Pattern compile = Pattern.compile(pointCut);


            String aspectClass = this.config.getAspectClass();
            Class<?> clazz = Class.forName(aspectClass);

            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method method : clazz.getMethods()) {
                String methodString = method.toString();
                if (methodString.contains("throws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = compile.matcher(methodString);
                if (matcher.matches()) {
                    //执行连接器
                    List<Object> advices = new LinkedList<>();
                    //吧MethodIterceptor
                    if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore()))) {
                        //创建一个Advivce
                        advices.add(new HYMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()), clazz.newInstance()));
                    }
                    //after
                    if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        //创建一个Advivce
                        advices.add(new HYAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectAfter()), clazz.newInstance()));
                    }
                    //afterThrowing
                    if (!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        //创建一个Advivce
                        HYAfterThrowingAdviceInterceptor throwingAdvice =
                                new HYAfterThrowingAdviceInterceptor(
                                        aspectMethods.get(config.getAspectAfterThrow()),
                                        clazz.newInstance());
                        throwingAdvice.setThrowingName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(method, advices);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
