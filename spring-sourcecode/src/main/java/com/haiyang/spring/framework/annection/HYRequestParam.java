package com.haiyang.spring.framework.annection;

import java.lang.annotation.*;

/**
 * @ClassName: HYRequestParam
 * @Description: RequestParam注解
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:38
 * @Version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HYRequestParam {

    String value() default "";


}
