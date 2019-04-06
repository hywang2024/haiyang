package com.haiyang.strategy;

/**
 * @ClassName: Payment
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 15:07
 * @Version 1.0
 */
public abstract class Payment {


    //支付类型
    public abstract String getName();

    //查询余额
    protected abstract double queryBalance(String uid);

    //扣款支付
    public ResultData pay(String uid, double amount) {
        if(queryBalance(uid) < amount){
            return new ResultData(500,"支付失败","余额不足");
        }
        return new ResultData(200,"支付成功","支付金额：" + amount);
    }
}
