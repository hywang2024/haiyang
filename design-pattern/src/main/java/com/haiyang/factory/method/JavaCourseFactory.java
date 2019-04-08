package com.haiyang.factory.method;

import com.haiyang.factory.ICourse;
import com.haiyang.factory.JavaCourse;

/**
 * @ClassName: JavaCourseFactory
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:25
 * @Version 1.0
 */
public class JavaCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}