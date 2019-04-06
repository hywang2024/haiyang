package com.haiyang.proxy.hyproxy;

import com.haiyang.proxy.jdkproxy.Girl;
import com.haiyang.proxy.staticproxy.Person;

/**
 * @ClassName: HYProxyTest
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 11:13
 * @Version 1.0
 */
public class HYProxyTest {
    public static void main(String[] args) {
        try {
            //JDK动态代理的实现原理
            Person obj = (Person) new HYMeiPo().getInstance(new Girl());
            System.out.println(obj.getClass());
            obj.findLove();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
