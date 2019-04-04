package com.haiyang.singleton;

/**
 * @ClassName: HungrySingleton
 * @Description: 单例模式 饿汉式
 * @Author: hywang
 *
 *  它式在类加载的时候就立即初始化，并创建单利对象
 *      优点：没有任何加锁，执行效率比较高 ，在用户体验上必懒汉模式更好（效率高）
 *      缺点：类加载的时候初始化，不管用不用都回占用着内存（占用内存）
 *      绝对线程安全，在线程没有初始化的时候就实例化类，不可能存在访问安全问题
 *
 * @CreateDate: 2019/3/18 4:40 PM
 * @Version: 1.0
 */
public class HungrySingleton {
    //加载顺序
    //先静态，后动态，先属性，后方法，先上后下
    private static final HungrySingleton singleton = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getSingleton() {
        return singleton;
    }
}
