package com.haiyang.observer.jdk;

import com.haiyang.observer.Question;
import com.haiyang.observer.Teacher;

/**
 * @ClassName: ObserverJDKTest
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 19:36
 * @Version: 1.0
 */
public class ObserverJDKTest {

    public static void main(String[] args) {
        ObserverPerson person = ObserverPerson.getInstance();
        Teacher tom = new Teacher("Tom");
        Teacher mic = new Teacher("Mic");


        //这为没有@Tom老师
        Question question = new Question();
        question.setUserName("小明");
        question.setContent("观察者设计模式适用于哪些场景？");
        person.addObserver(tom);
        person.addObserver(mic);
        person.publishQuestion(question);
    }
}
