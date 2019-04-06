package com.haiyang.delegate.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Leader
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:01
 * @Version 1.0
 */
public class Leader implements IEmployee {
    private Map<String, IEmployee> targets = new HashMap<String, IEmployee>();

    public Leader() {
        targets.put("加密", new EmployeeA());
        targets.put("登录", new EmployeeB());
    }

    @Override
    public void doing(String command) {
        targets.get(command).doing(command);
    }
}
