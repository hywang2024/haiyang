package com.haiyang.delegate.simple;

/**
 * @ClassName: Boss
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:02
 * @Version 1.0
 */
public class Boss {
    public void command(String command, Leader leader) {
        leader.doing(command);
    }
}
