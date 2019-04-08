package com.haiyang.decorator;

/**
 * @ClassName: DecoratorTest
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-08 17:46
 * @Version: 1.0
 */
public class DecoratorTest {

    public static void main(String[] args) {

        BeefNoodles beefNoodles;
        beefNoodles = new BaseBeefNoodles();
        //加份面
        beefNoodles = new NoodlesDecorator(beefNoodles);
        //加个蛋
        beefNoodles = new EggDecorator(beefNoodles);

        System.out.println(beefNoodles.getPrice() + "," + beefNoodles.getIntroduce());

    }
}
