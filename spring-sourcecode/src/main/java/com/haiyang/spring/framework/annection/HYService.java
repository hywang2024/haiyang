package com.haiyang.spring.framework.annection;

import java.lang.annotation.*;

/**
 * @ClassName: HYService
 * @Description: service 注解
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:33
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HYService {
    String value() default  "";
}
