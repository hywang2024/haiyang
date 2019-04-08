package com.haiyang.observer.guava;

import com.haiyang.observer.Question;

/**
 * @ClassName: ObserverGuavaTest
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 19:41
 * @Version: 1.0
 */
public class ObserverGuavaTest {
    public static void main(String[] args) {
        ObserverPersonEvent event = ObserverPersonEvent.getInstance();
        Question question = new Question();
        question.setUserName("haiyang");
        question.setContent("发布文章");
        event.publishQuestion(question);
    }
}
