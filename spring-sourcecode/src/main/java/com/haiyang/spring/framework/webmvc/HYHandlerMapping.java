package com.haiyang.spring.framework.webmvc;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @ClassName: HYHandlerMapping
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-29 12:02
 * @Version: 1.0
 */
@Data
public class HYHandlerMapping {
    /*
     * 保存方法对应的实例
     */
    private Object controller;
    /**
     * 保存映射的方法
     */
    private Method method;
    /**
     * url的正则
     */
    private Pattern pattern;    //URL的正则匹配

    public HYHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }
}
