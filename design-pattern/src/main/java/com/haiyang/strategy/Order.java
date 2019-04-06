package com.haiyang.strategy;

/**
 * @ClassName: Order
 * @Description:  支付
 * @Author Administrator
 * @CreateDate 2019/4/6 15:04
 * @Version 1.0
 */
public class Order {

    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    //完美地解决了switch的过程，不需要在代码逻辑中写switch了
    //更不需要写if    else if
    public ResultData pay(){
        return pay(PayStrategy.DEFAULT_PAY);
    }

    public ResultData pay(String payKey){
        Payment payment = PayStrategy.get(payKey);
        System.out.println("欢迎使用" + payment.getName());
        System.out.println("本次交易金额为：" + amount + "，开始扣款...");
        return payment.pay(uid,amount);
    }
}
