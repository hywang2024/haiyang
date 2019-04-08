package com.haiyang.decorator;

/**
 * @ClassName: NoodlesDecorator
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 17:42
 * @Version: 1.0
 */
public class NoodlesDecorator extends BeefNoodles {

    private static int ONE_NOODLES = 3;

    private BeefNoodles beefNoodles;

    public NoodlesDecorator(BeefNoodles beefNoodles) {
        this.beefNoodles = beefNoodles;
    }

    @Override
    int getPrice() {
        return beefNoodles.getPrice() + ONE_NOODLES;
    }

    @Override
    String getIntroduce() {
        String introduce = ",加一份面，更实惠";
        return beefNoodles.getIntroduce() + introduce;
    }
}
