package com.haiyang.singleton;

/**
 * @ClassName: HungryStaticSingleton
 * @Description:   饿汉式 静态块单利
 * @Author: hywang
 * @CreateDate: 2019/3/18 4:51 PM
 * @Version: 1.0
 */
public class HungryStaticSingleton {
    private static HungryStaticSingleton singleton ;
    static {
        singleton = new HungryStaticSingleton();
    }

    private HungryStaticSingleton() {
    }

    public static HungryStaticSingleton getSingleton() {
        return singleton;
    }
}
