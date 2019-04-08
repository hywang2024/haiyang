package com.haiyang.observer;

import com.haiyang.observer.jdk.ObserverPerson;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName: Teacher
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 19:33
 * @Version: 1.0
 */
public class Teacher implements Observer {

    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        ObserverPerson person = (ObserverPerson)o;
        Question question = (Question)arg;
        System.out.println("===============================");
        System.out.println(name + "老师，你好！\n" +
                "您收到了一个来自“" + person.getName() + "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent() + "\n" +
                "提问者：" + question.getUserName());
    }
}
