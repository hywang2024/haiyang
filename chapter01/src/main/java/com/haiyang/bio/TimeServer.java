package com.haiyang.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: BioServer
 * @Description: bio server
 * @Author: hywang
 * @CreateDate: 2018/11/12 11:51 AM
 * @Version: 1.0
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 10086;
        if (null != args && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                port = 10086;
            }

        }
        ServerSocket serverSocket = null;
        try {
            serverSocket= new ServerSocket(port);
            System.out.println("time server start,port:"+port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandle(socket)).start();
            }
        }catch (Exception e){

        }finally {
            if(serverSocket != null){
                System.out.println("time server start stop");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket=null;
            }
        }

    }
}
