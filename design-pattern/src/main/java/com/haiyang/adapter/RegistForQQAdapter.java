package com.haiyang.adapter;

/**
 * @ClassName: RegistForQQAdapter
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:30
 * @Version 1.0
 */
public class RegistForQQAdapter implements RegistAdapter,LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return false;
    }

    @Override
    public ResultData login(String id, Object adapter) {
        return null;
    }
}
