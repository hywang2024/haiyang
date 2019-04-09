package com.haiyang.factory.simple;

import com.haiyang.factory.ICourse;
import com.haiyang.factory.JavaCourse;

/**
 * @ClassName: SimpleFactoryTestr
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:23
 * @Version 1.0
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        CourseFactory factory = new CourseFactory();
        ICourse course = (ICourse) factory.createByClass(JavaCourse.class);
        course.record();
    }
}
