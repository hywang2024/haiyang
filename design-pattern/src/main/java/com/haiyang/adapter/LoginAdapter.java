package com.haiyang.adapter;

/**
 * @ClassName: LoginAdapter
 * @Description: 在适配器里面，这个接口是可有可无，不要跟模板模式混淆
 * 模板模式一定是抽象类，而这里仅仅只是一个接口
 * @Author Administrator
 * @CreateDate 2019/4/7 19:27
 * @Version 1.0
 */
public interface LoginAdapter {
    boolean support(Object adapter);

    ResultData login(String id, Object adapter);
}
