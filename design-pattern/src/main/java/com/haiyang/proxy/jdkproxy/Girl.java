package com.haiyang.proxy.jdkproxy;

import com.haiyang.proxy.staticproxy.Person;

/**
 * @ClassName: Girl
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 10:50
 * @Version 1.0
 */
public class Girl implements Person {
    @Override
    public void findLove() {
        System.out.println("高富帅");
        System.out.println("身高180cm");
        System.out.println("有6块腹肌");
    }
}
