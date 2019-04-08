package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: JavaVideo
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:31
 * @Version 1.0
 */
public class GoVideo implements IVideo {
    @Override
    public void record() {
        System.out.println("录制go视频");
    }
}
