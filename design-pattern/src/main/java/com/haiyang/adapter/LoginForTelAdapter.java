package com.haiyang.adapter;

/**
 * @ClassName: LoginForTelAdapter
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:29
 * @Version 1.0
 */
public class LoginForTelAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }

    @Override
    public ResultData login(String id, Object adapter) {
        return null;
    }
}
