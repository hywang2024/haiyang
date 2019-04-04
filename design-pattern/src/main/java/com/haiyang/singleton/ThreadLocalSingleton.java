package com.haiyang.singleton;

/**
 * @ClassName: ThreadLocalSingleton
 * @Description:
 * @Author: hywang
 *
 * @CreateDate: 2019/3/18 5:23 PM
 * @Version: 1.0
 */
public class ThreadLocalSingleton {
    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance =
            new ThreadLocal<ThreadLocalSingleton>(){
                @Override
                protected ThreadLocalSingleton initialValue() {
                    return new ThreadLocalSingleton();
                }
            };

    private ThreadLocalSingleton(){}

    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get();
    }
}
