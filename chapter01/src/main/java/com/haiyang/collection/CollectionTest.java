package com.haiyang.collection;

import java.util.*;

/**
 * @ClassName: CollectionTest
 * @Description: 集合
 * @Author: hywang
 * @CreateDate: 2019/3/6 6:12 PM
 * @Version: 1.0
 */
public class CollectionTest {

    public static void main(String[] args) {
        String[] arr = {"1","a", "b", "c"};
        List<String> list = new ArrayList();
        list.add("1");
        Collections.addAll(list, arr);

        System.out.println(list);

        Set<String> set = new HashSet<>();
        set.add("1");
        Collections.addAll(set, arr);
        System.out.println(set);

    }



}
