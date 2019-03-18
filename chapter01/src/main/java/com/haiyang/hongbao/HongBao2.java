package com.haiyang.hongbao;

import java.util.concurrent.*;

/**
 * @ClassName: HongBao2
 * @Description:   信号量
 * @Author: hywang
 * @CreateDate: 2018/11/21 3:34 PM
 * @Version: 1.0
 */
public class HongBao2 {

    // 初始化红包 ，此处使用阻塞队列实现
    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
    //初始化化红包
    static {
        for (int i = 0; i < 10; i++) {
            queue.add(i + "");
        }
    }
    private static final Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) {
        // 只能允许5个客户端线程访问服务器资源

        // 新建30个线程，模拟客户端同时有30个请求线程
        ExecutorService service = Executors.newFixedThreadPool(100);
        // 模拟提交30个请求给服务器
        for(int i=0; i<100; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取许可
                        semaphore.acquire();
                        //System.out.println(Thread.currentThread().getName()+"线程持有信号量");
                        // 模拟请求服务器资源
                        queue.poll();
                        if (queue.size() == 0) {
                            System.out.println("红包已经抢完了");
                        } else {
                            System.out.println("红包还剩下：" + queue.toString());
                        }
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // 释放线程池资源
        service.shutdown();

    }
}
