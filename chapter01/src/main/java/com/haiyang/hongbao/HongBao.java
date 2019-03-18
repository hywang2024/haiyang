package com.haiyang.hongbao;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HongBao
 * @Description: 1000 个人抢 10个红包
 * @Author: hywang
 * @CreateDate: 2018/11/21 2:11 PM
 * @Version: 1.0
 */
public class HongBao {
    // 初始化红包 ，此处使用阻塞队列实现
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
    //初始化化红包
    static {
        for (int i = 0; i < 10; i++) {
            queue.add(i + "");
        }
    }
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 100, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        //模拟多人抢红包
        for (int i = 0; i < 100; i++) {
            executor.execute(new Qhb(queue));
        }
        executor.shutdown();
    }

    static class Qhb implements Runnable{
        private Queue<String> queue;
        public Qhb(Queue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            if (queue.size() != 0) {
                synchronized (HongBao.class) {
                    queue.poll();
                    if (queue.size() == 0) {
                        System.out.println("红包已经抢完了");
                    } else {
                        System.out.println("红包还剩下：" + queue.toString());
                    }
                }

            } else {
                System.out.println("红包已经抢完了");
            }
        }
    }
}
