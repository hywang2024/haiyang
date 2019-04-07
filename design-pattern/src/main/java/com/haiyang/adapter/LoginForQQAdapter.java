package com.haiyang.adapter;

/**
 * @ClassName: LoginForQQAdapter
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:28
 * @Version 1.0
 */
public class LoginForQQAdapter implements  LoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ResultData login(String id, Object adapter) {
        return null;
    }
}
