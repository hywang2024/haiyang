package com.haiyang.adapter;

/**
 * @ClassName: RegistAdapter
 * @Description:  注册适配器
 * @Author Administrator
 * @CreateDate 2019/4/7 19:28
 * @Version 1.0
 */
public interface RegistAdapter {
    boolean support(Object adapter);
    ResultData login(String id, Object adapter);
}
