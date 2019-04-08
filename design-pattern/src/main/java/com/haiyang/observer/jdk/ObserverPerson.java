package com.haiyang.observer.jdk;

import com.haiyang.observer.Question;

import java.util.Observable;

/**
 * @ClassName: ObserverPerson
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 19:26
 * @Version: 1.0
 */
public class ObserverPerson extends Observable {

    private static String name = "时间发布者";

    public void publishQuestion(Question question) {
        System.out.println(question.getUserName() + "在" + this.name + "上提交了一个问题。");
        setChanged();
        notifyObservers(question);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ObserverPerson.name = name;
    }


    private static ObserverPerson person = null;

    private ObserverPerson() {
    }

    public static ObserverPerson getInstance() {
        if (null == person) {
            person = new ObserverPerson();
        }
        return person;
    }
}
