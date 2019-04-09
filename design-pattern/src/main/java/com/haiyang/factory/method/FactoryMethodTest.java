package com.haiyang.factory.method;

import com.haiyang.factory.ICourse;

/**
 * @ClassName: FactoryMethodTest
 * @Description: TODO
 * @Author Administrator
 * @CreateDate 2019/4/8 21:26
 * @Version 1.0
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        ICourseFactory factory = new GoCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new JavaCourseFactory();
        course = factory.create();
        course.record();
    }
}
