package com.haiyang.singleton;

/**
 * @ClassName: LazyDubboCheckSingleton
 * @Description: 双重校验锁
 * @Author: hywang
 * @CreateDate: 2019/3/18 4:54 PM
 * @Version: 1.0
 */
public class LazyDubboCheckSingleton {
    private volatile static LazyDubboCheckSingleton singleton = null;

    private LazyDubboCheckSingleton() {
    }

    public static LazyDubboCheckSingleton getSingleton() {
        if (null == singleton) {
            synchronized (LazyDubboCheckSingleton.class) {
                if (null == singleton) {
                    singleton = new LazyDubboCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
