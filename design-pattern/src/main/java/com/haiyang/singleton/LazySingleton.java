package com.haiyang.singleton;

/**
 * @ClassName: LazySingleton
 * @Description:
 * @Author: hywang
 *  饿汉式
 *  在外部需要使用的时候才初始化
 *
 * @CreateDate: 2019/3/18 4:48 PM
 * @Version: 1.0
 */
public class LazySingleton {
    private static LazySingleton singleton = null;

    private LazySingleton() {
    }

    public static LazySingleton getSingleton() {
        if(null==singleton){
            singleton = new LazySingleton();
        }
        return singleton;
    }
}
