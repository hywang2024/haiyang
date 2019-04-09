package com.haiyang.factory.simple;

import com.haiyang.factory.GoCourse;
import com.haiyang.factory.ICourse;
import com.haiyang.factory.JavaCourse;

/**
 * @ClassName: CourseFactory
 * @Description: 简单工厂
 * @Author Administrator
 * @CreateDate 2019/4/8 21:14
 * @Version 1.0
 */
public class CourseFactory {

    public ICourse create(String key) {
        if ("java".equalsIgnoreCase(key)) {
            return new JavaCourse();
        } else if ("go".equalsIgnoreCase(key)) {
            return new GoCourse();
        } else {
            return null;
        }
    }

    public Object createByName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object createByClass(Class<? extends ICourse> clazz){
        if(null!=clazz){
            try {
                return  clazz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
