package com.haiyang.spring.framework.annection;

import java.lang.annotation.*;

/**
 * @ClassName: HYAutowired
 * @Description:  注入 注解
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:35
 * @Version 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HYAutowired {
    String value() default  "";
}
