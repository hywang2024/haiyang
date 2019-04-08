package com.haiyang.decorator;

/**
 * @ClassName: BeefNoodlesDecorator
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 17:38
 * @Version: 1.0
 */
public abstract class BeefNoodlesDecorator extends  BeefNoodles{

    private BeefNoodles beefNoodles;

    public BeefNoodlesDecorator(BeefNoodles beefNoodles) {
        this.beefNoodles = beefNoodles;
    }

    abstract String doSomeString();

    @Override
    int getPrice() {
        return beefNoodles.getPrice();
    }

    @Override
    String getIntroduce() {
        return beefNoodles.getIntroduce();
    }
}
