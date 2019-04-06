package com.haiyang.proxy.staticproxy;

/**
 * @ClassName: StaticProxyTest
 * @Description: 测试
 * @Author Administrator
 * @CreateDate 2019/4/6 10:48
 * @Version 1.0
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        Mother father = new Mother(new Son());
        father.findLove();
    }
}
