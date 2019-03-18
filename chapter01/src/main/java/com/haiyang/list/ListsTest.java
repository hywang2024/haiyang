package com.haiyang.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ListsTest
 * @Description: todo java类作用描述
 * @Author: hywang
 * @CreateDate: 2019/1/14 4:09 PM
 * @Version: 1.0
 */
public class ListsTest {
    public static void main(String[] args) {
        List<String>  list1=new ArrayList<>();
        list1.add("123,福林");
        list1.add("143,福林");
        list1.add("153,福林");
        list1.add("163,福林");
        list1.add("173,福林");
        list1.add("183,福林");
        list1.add("193,福林");
        list1.add("223,福林");
        list1.add("323,福林");
        list1.add("423,福林");
        list1.add("523,福林");
        list1.add("623,福林");
        list1.add("723,福林");
        list1.add("823,福林");
        list1.add("923,福林");
        List<String> list2= new ArrayList<>();
        list2.add("123");
        list2.add("423");

        //list1.removeIf();
        list2.forEach(var -> {
            list1.removeIf(var1 -> var1.contains(var));
        });

        list1.forEach(var->{
            System.out.println(var);
        });
        System.out.println(list1.size());

    }
}
