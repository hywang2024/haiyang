package com.haiyang.single;

/**
 * @ClassName: Singleton
 * @Description: 单利模式的几种写法
 * @Author: hywang
 * @Version: 1.0
 */
public class Singleton {

    public static void main(String[] args) {

    }

}

/**
 * 懒汉式，线程不安全的
 */

class Singleton_Lanhan {
    private static Singleton_Lanhan singleton;

    public Singleton_Lanhan() {
    }

    public static /*synchronized */ Singleton_Lanhan getInstance() {
        if (null == singleton) {
            //如果两个线程同时 进入到这里，就会产生两个对象，所以线程不安全
            //修改点以，可以在此方法 加synchronized ，这样就可以保证线程安全
            singleton = new Singleton_Lanhan();
        }
        return singleton;
    }
}

/**
 * 懒汉式
 * 这种方式比较常用，但容易产生垃圾对象。
 * 优点：没有加锁，执行效率会提高。
 * 缺点：类加载时就初始化，浪费内存。
 */
class Singleton_EHan {
    private static Singleton_EHan singleton = new Singleton_EHan();

    public Singleton_EHan() {
    }

    private static Singleton_EHan getInstance() {
        return singleton;
    }
}

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 * 这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 * getInstance() 的性能对应用程序很关键。
 */

class Singleton_DoubleLock {
    private volatile static Singleton_DoubleLock singleton;

    public Singleton_DoubleLock() {
    }

    public static Singleton_DoubleLock getInstance() {
        if (null == singleton) {
            synchronized (Singleton_DoubleLock.class) {
                if (null == singleton) {
                    singleton = new Singleton_DoubleLock();
                }
            }
        }
        return singleton;
    }
}

/**
 * 静态内部类
 * 这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。
 * 这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用
 * 这种方式同样利用了 classloader 机制来保证初始化 instance 时只有一个线程
 */
class Singleton_StaicClass{
    private static class SingletonHolder{
        private static Singleton_StaicClass INSTANCE = new Singleton_StaicClass();
    }

    public Singleton_StaicClass() {
    }

    public static Singleton_StaicClass getInstance(){
        return SingletonHolder.INSTANCE;
    }
}

/**
 * 枚举类
 * 种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。
 * 它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。
 */
enum Singleton_Enum{
    INSTANCE;
}