package com.haiyang.spring.demo.service.impl;

import com.haiyang.spring.demo.service.IDemoService;
import com.haiyang.spring.framework.annection.HYService;

/**
 * @ClassName: DemoServiceImpl
 * @Description:  demo 实现
 * @Author Administrator
 * @CreateDate 2019/4/13 11:19
 * @Version 1.0
 */
@HYService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String getName(String name) {
        return "My name is " + name;
    }
}
