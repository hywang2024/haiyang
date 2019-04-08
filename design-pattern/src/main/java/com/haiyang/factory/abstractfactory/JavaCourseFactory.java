package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: JavaCourseFactory
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:30
 * @Version 1.0
 */
public class JavaCourseFactory implements CourseFactory {
    @Override
    public INote createNote() {
        return new JavaNote();
    }

    @Override
    public IVideo createVideo() {
        return new JavaVideo();
    }
}
