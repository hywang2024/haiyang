package com.haiyang.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: BIOServer
 * @Description: 同步阻塞
 * @Author: hywang
 * @CreateDate: 2019-06-19 11:35
 * @Version: 1.0
 */
public class BIOServer {

    //服务端网络IO模型的封装对象
    private ServerSocket serverSocket;

    public BIOServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("bio server start,port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listen() throws IOException {
        //循环监听
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("listen the port is " + socket.getPort());
            //读取 传输数据
            InputStream is = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len = is.read(buff);
            if (len > 0) {
                String data = new String(buff, 0, len);
                System.out.println("listen data :" + data);
            }


        }
    }

    public static void main(String[] args) throws IOException {
        new BIOServer(8080).listen();

    }
}
