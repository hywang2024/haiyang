package com.haiyang.decorator;

/**
 * @ClassName: BeefNoodles
 * @Description: 牛肉面
 * @Author: hywang
 * @CreateDate: 2019-04-08 17:34
 * @Version: 1.0
 */
public class BaseBeefNoodles extends BeefNoodles {

    private static int DEFAULT_PRICE = 12;

    @Override
    int getPrice() {
        return DEFAULT_PRICE;
    }

    @Override
    String getIntroduce() {
        return "正宗中华牛肉面。。。。。";
    }
}
