package com.haiyang.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @ClassName: BIOClient
 * @Description: client
 * @Author: hywang
 * @CreateDate: 2019-06-19 11:46
 * @Version: 1.0
 */
public class BIOClient {

    public static void main(String[] args) throws IOException {
        //要和谁进行通信，服务器IP、服务器的端口
        Socket socket = new Socket("127.0.0.1", 8080);
        //不管是客户端还是服务端，都有可能write和read
        OutputStream outputStream = socket.getOutputStream();
        String uuid = UUID.randomUUID().toString();
        outputStream.write(uuid.getBytes());
        outputStream.close();
        socket.close();
    }
}
