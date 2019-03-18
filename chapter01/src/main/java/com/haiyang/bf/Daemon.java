package com.haiyang.bf;

/**
 * @ClassName: Daemon
 * @Description: 守护线程
 * @Author: hywang
 * @CreateDate: 2018/12/18 12:55 PM
 * @Version: 1.0
 */
public class Daemon {
    public static class DaemonT extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("i am alive");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new DaemonT();
        //thread.setDaemon(true);
        thread.start();
        Thread.sleep(2000);
    }
}
