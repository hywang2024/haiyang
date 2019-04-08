package com.haiyang.factory.method;

import com.haiyang.factory.GoCourse;
import com.haiyang.factory.ICourse;

/**
 * @ClassName: JavaCourseFactory
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:25
 * @Version 1.0
 */
public class GoCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new GoCourse();
    }
}