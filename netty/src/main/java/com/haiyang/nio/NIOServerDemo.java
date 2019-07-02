package com.haiyang.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NIOServerDemo
 * @Description: nio
 * @Author: hywang
 * @CreateDate: 2019-06-19 11:51
 * @Version: 1.0
 */
public class NIOServerDemo {

    private int port;
    //轮训器
    private Selector selector;
    //缓存区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NIOServerDemo(int port) {
        this.port = port;
        try {
            ServerSocketChannel server = ServerSocketChannel.open();

            server.bind(new InetSocketAddress(port));
            //设置非阻塞
            server.configureBlocking(false);

            selector = Selector.open();

            server.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException {
        //轮训
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //每一个key代表一种状态
                //没一个号对应一个业务
                //数据就绪、数据可读、数据可写
                process(selectionKey);
            }
        }

    }

    private void process(SelectionKey selectionKey) throws IOException {
        //针对于每一种状态给一个反应
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
            //这个方法体现非阻塞，不管你数据有没有准备好
            //你给我一个状态和反馈
            SocketChannel channel = server.accept();
            //设置为非阻塞
            channel.configureBlocking(false);
            //当数据准备就绪的时候，将状态改为可读
            channel.register(selector, SelectionKey.OP_READ);

        } else if (selectionKey.isReadable()) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            int len = channel.read(buffer);
            if (len > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                selectionKey = channel.register(selector, SelectionKey.OP_WRITE);
                //在key上携带一个附件，一会再写出去
                selectionKey.attach(content);
                System.out.println("读取内容：" + content);
            }


        } else if (selectionKey.isWritable()) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();

            String content = (String) selectionKey.attachment();
            channel.write(ByteBuffer.wrap(("输出：" + content).getBytes()));

            channel.close();
        }

    }

    public static void main(String[] args) throws IOException {
        new NIOServerDemo(8080).listen();
    }
}
