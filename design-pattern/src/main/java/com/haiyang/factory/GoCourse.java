package com.haiyang.factory;

/**
 * @ClassName: GoCourse
 * @Description: TODO
 * @Author Administrator
 * @CreateDate 2019/4/8 21:13
 * @Version 1.0
 */
public class GoCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制go视频");
    }
}
