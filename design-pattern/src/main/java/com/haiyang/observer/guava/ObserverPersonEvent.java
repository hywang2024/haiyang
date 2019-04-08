package com.haiyang.observer.guava;

import com.google.common.eventbus.EventBus;
import com.haiyang.observer.Question;
import com.haiyang.observer.Teacher;

/**
 * @ClassName: ObserverPersonEvent
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 19:40
 * @Version: 1.0
 */
public class ObserverPersonEvent {
    private String name = "**论坛";
    private static ObserverPersonEvent personEvent = null;

    private ObserverPersonEvent() {
    }

    public static ObserverPersonEvent getInstance() {
        if (null == personEvent) {
            personEvent = new ObserverPersonEvent();
        }
        return personEvent;
    }

    public String getName() {
        return name;
    }

    public void publishQuestion(Question question) {
        System.out.println(question.getUserName() + "在" + this.name + "上提交了一个问题。 ");
        EventBus eventBus = new EventBus();
        eventBus.register(new Teacher("haiyang"));
        eventBus.post(question);
    }
}
