package com.haiyang.factory;

/**
 * @ClassName: JavaCourse
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:12
 * @Version 1.0
 */
public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制java视频");
    }
}
