package com.haiyang.singleton;

/**
 * @ClassName: EnumSingleton
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019/3/18 5:05 PM
 * @Version: 1.0
 */
public enum EnumSingleton {
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
