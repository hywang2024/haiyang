package com.haiyang.decorator;

/**
 * @ClassName: NoodlesDecorator
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 17:42
 * @Version: 1.0
 */
public class EggDecorator extends BeefNoodles {

    private static int ONE_NOODLES = 2;

    private BeefNoodles beefNoodles;

    public EggDecorator(BeefNoodles beefNoodles) {
        this.beefNoodles = beefNoodles;
    }

    @Override
    int getPrice() {
        return beefNoodles.getPrice() + ONE_NOODLES;
    }

    @Override
    String getIntroduce() {
        String introduce = ",加个蛋，营养更均衡";
        return beefNoodles.getIntroduce() + introduce;
    }
}
