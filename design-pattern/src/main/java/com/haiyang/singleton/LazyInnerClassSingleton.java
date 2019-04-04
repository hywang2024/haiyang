package com.haiyang.singleton;

/**
 * @ClassName: LazyInnerClassSingleton
 * @Description:
 * @Author: hywang
 *
 * 这种形式万千坚固饿汉式的内存浪费，也兼顾synchronized的性能问题
 *
 * @CreateDate: 2019/3/18 4:57 PM
 * @Version: 1.0
 */
public class LazyInnerClassSingleton {

    //默认使用的时候，回先初始化内部类
    //如果没有使用的话，内部式不加载的
    private LazyInnerClassSingleton() {
        if (null != SingletonHodler.SINGLETON) {
            throw new RuntimeException("not allowed to be created instance");
        }
    }

    //默认不加载
    private static class SingletonHodler {
        private static final LazyInnerClassSingleton SINGLETON = new LazyInnerClassSingleton();
    }

    // static 式为了使单利的空间共享
    //finall 保证方法不被重写，重载
    public static final LazyInnerClassSingleton getInstance() {
        return SingletonHodler.SINGLETON;
    }
}
