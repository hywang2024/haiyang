package com.haiyang.spring.demo.controller;

import com.haiyang.spring.demo.service.IDemoService;
import com.haiyang.spring.framework.annection.HYAutowired;
import com.haiyang.spring.framework.annection.HYController;
import com.haiyang.spring.framework.annection.HYRequestMapping;
import com.haiyang.spring.framework.annection.HYRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: DemoController
 * @Description:
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 11:22
 * @Version 1.0
 */
@HYController
@HYRequestMapping("/demo")
public class DemoController {

    @HYAutowired
    private IDemoService demoService;

    @HYRequestMapping("/getName")
    public void getName(HttpServletRequest req, HttpServletResponse resp, @HYRequestParam("name") String name) {
        try {
            //String out = demoService.getName(name);
            resp.getWriter().write("/getName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
