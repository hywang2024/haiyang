package com.haiyang.exception;

/**
 * @ClassName: ExceptionCatch
 * @Description: todo java类作用描述
 * @Author: hywang
 * @CreateDate: 2019/1/4 2:22 PM
 * @Version: 1.0
 */
public class ExceptionCatch {

    public static void main(String[] args) throws Exception {

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            System.out.println("catch");
            throw new Exception();
        } finally {
            System.out.println("finally");
        }
    }
}
