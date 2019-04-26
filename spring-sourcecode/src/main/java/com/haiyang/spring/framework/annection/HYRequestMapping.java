package com.haiyang.spring.framework.annection;

import java.lang.annotation.*;

/**
 * @ClassName: HYRequestMapping
 * @Description: requestMapping注解
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:38
 * @Version 1.0
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HYRequestMapping {
    String value() default "";
}
