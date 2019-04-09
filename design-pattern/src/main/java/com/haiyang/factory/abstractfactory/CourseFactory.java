package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: CourseFactory
 * @Description: 抽象工厂是用户的主入口
 *   在Spring中应用得最为广泛的一种设计模式
 *   易于扩展
 * @Author Administrator
 * @CreateDate 2019/4/8 21:28
 * @Version 1.0
 */
public interface CourseFactory {
    INote createNote();

    IVideo createVideo();

}
