package com.haiyang.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: ContainerSingleton
 * @Description: Spring、中就是使用这种注册式单例
 * @Author: hywang
 * @CreateDate: 2019/3/18 5:07 PM
 * @Version: 1.0
 */
public class ContainerSingleton {
    private ContainerSingleton() {
    }

    private static Map<Object, Object> ioc = new ConcurrentHashMap<>();

    public static Object getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object obj = Class.forName(className).newInstance();
                ioc.put(className, obj);
                return obj;
            } else {
                return ioc.get(className);
            }
        }
    }
}
