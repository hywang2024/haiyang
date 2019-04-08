package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: AbstractFactoryTest
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:33
 * @Version 1.0
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        JavaCourseFactory factory = new JavaCourseFactory();
        factory.createNote().edit();
        factory.createVideo().record();

    }
}
