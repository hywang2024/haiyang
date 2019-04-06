package com.haiyang.strategy;

/**
 * @ClassName: AliPay
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:09
 * @Version 1.0
 */
public class AliPay extends Payment {
    @Override
    public String getName() {
        return "支付宝";
    }

    @Override
    protected double queryBalance(String uid) {
        return 900;
    }
}
