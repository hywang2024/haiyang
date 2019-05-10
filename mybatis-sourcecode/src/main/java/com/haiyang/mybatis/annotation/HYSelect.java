package com.haiyang.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: HYSelect
 * @Description: 注解方法 配置sql
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:11
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HYSelect {
    String value();
}
