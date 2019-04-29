package com.haiyang.spring.framework.webmvc;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName: HYModelAndView
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-29 14:29
 * @Version: 1.0
 */
@Data
public class HYModelAndView {

    private String viewName;
    private Map<String, ?> model;

    public HYModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public HYModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
}
