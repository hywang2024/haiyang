package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: JavaNote
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:31
 * @Version 1.0
 */
public class JavaNote implements INote {
    @Override
    public void edit() {
        System.out.println("编写Java笔记");
    }
}
