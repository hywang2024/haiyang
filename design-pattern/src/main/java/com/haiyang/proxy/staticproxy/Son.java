package com.haiyang.proxy.staticproxy;

/**
 * @ClassName: Son
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 10:45
 * @Version 1.0
 */
public class Son implements Person {
    @Override
    public void findLove(){
        System.out.println("儿子要求：白富美");
    }

    public void findJob(){ }

    public void eat(){ }
}
