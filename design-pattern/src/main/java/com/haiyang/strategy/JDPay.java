package com.haiyang.strategy;

/**
 * @ClassName: JDPay
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:10
 * @Version 1.0
 */
public class JDPay extends Payment {
    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    protected double queryBalance(String uid) {
        return 200;
    }
}
