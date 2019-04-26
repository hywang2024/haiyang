package com.haiyang.spring.framework.annection;

import java.lang.annotation.*;

/**
 * @ClassName: HYController
 * @Description: contrller 注解
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:25
 * @Version 1.0
 */
@Target({ElementType.TYPE})  //用于什么地方
// CONSTRUCTOR：构造器的声明
// FIELD：域声明（包括enum实例）
//  LOCAL_VARIABLE：局部变量声明
//   METHOD：方法声明
//  PACKAGE：包声明
//  PARAMETER：参数声明
//  TYPE：类、接口（包括注解类型）或enum声明
@Retention(RetentionPolicy.RUNTIME) //表示需要在什么级别保存该注解信息
//  SOURCE：注解将被编译器丢弃
//  CLASS：注解在class文件中可用，但会被VM丢弃
//  RUNTIME：VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
@Documented //将注解包含在Javadoc中

//@Inherited 允许子类继承父类中的注解
public @interface HYController {
    String value() default "";
}
