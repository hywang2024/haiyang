package com.haiyang.swap;

import java.lang.reflect.Field;

/**
 * @ClassName: SwapTest
 * @Description:  交换两个interger值
 * @Author: hywang
 * @CreateDate: 2018/11/21 10:36 AM
 * @Version: 1.0
 */
public class SwapTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer integer1 = 1;
        Integer integer2 = 2;
        swap(integer1,integer2);
        System.out.println(integer1 +"," + integer2);
    }

    public static void swap(Integer integer1,Integer integer2) throws NoSuchFieldException, IllegalAccessException {
       /* Integer temp = Integer.valueOf(integer1);
        integer1 = Integer.valueOf(integer2);
        integer2 = Integer.valueOf(temp);*/

       //利用反射获取到Integer
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        Integer temp = new Integer(integer1);
        value.set(integer1,integer2);
        value.set(integer2,temp);
    }
}
