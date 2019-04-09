package com.haiyang.factory.abstractfactory;

/**
 * @ClassName: JavaNote
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/8 21:31
 * @Version 1.0
 */
public class GoNote implements INote {
    @Override
    public void edit() {
        System.out.println("编写go笔记");
    }
}
