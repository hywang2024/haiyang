package com.haiyang.strategy;

/**
 * @ClassName: AliPay
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:09
 * @Version 1.0
 */
public class WechatPay extends Payment {
    @Override
    public String getName() {
        return "微信";
    }

    @Override
    protected double queryBalance(String uid) {
        return 1900;
    }
}
