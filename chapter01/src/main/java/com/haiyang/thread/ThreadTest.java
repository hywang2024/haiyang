package com.haiyang.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName: ThreadTest
 * @Description: 当前有1000个有序的任务，要求利用100个线程处理这些任务，并在完成所有任务后保证原有顺序返回，如何设计算法（描述思路即可）？
 * @Author: hywang
 * @CreateDate: 2019/3/8 4:42 PM
 * @Version: 1.0
 */
public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ThreadTest().process();
    }

    public void process() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CallableTask task = new CallableTask(i);
            Future<String> submit = executorService.submit(task);
            futures.add(submit);
        }
        for (int j = 0; j < futures.size(); j++) {
            System.out.println("按顺序取结果：" + futures.get(j).get());
        }
        System.out.println("执行结束");
        executorService.shutdown();
    }

    class CallableTask implements Callable<String> {
        Integer temp;

        public CallableTask(Integer temp) {
            this.temp = temp;
        }

        @Override
        public String call() {
            System.out.println("执行第" + temp + "个任务");
            return "第" + temp + "个任务：" + Thread.currentThread().getName();
        }
    }
}
