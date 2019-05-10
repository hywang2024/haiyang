package com.haiyang.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: HYEntity
 * @Description: 实体类注解
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:10
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HYEntity {
    Class<?> value();
}
