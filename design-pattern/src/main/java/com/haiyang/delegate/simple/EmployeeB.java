package com.haiyang.delegate.simple;

/**
 * @ClassName: EmployeeA
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:00
 * @Version 1.0
 */
public class EmployeeB implements IEmployee {
    @Override
    public void doing(String command) {
        System.out.println("我是员工B，我现在开始干" + command + "工作");
    }
}
