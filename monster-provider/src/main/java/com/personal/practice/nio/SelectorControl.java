package com.personal.practice.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Set;

/**
 * what nio is
 */
public class SelectorControl {
    public static void main(String[] args) {
        try {
            //FileChannel不能设为非阻塞模式,不能在多路复用中被监听
            //只有继承SelectableChannel抽象类的channel才能被监听
            Selector selector = Selector.open();

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                selector.select(1000);
                Set<SelectionKey>  selectionKeys=selector.selectedKeys();
                if (selectionKeys!=null){
                    for (SelectionKey selectionKey:selectionKeys){
                        if ((selectionKey.readyOps()&SelectionKey.OP_ACCEPT)!=0){
                            SocketChannel socketChannel=((ServerSocketChannel)selectionKey.channel()).accept();
                            //TODO 这里为什么会为null 1.因为没有remove！2.select超过1000
                            if (socketChannel!=null){
                                System.out.println(socketChannel.getRemoteAddress());
                                System.out.println("+++++++++");
                                //TODO 不要忘记close
                                socketChannel.close();
                            }

                        }
                    }
                    //TODO 不要忘记clear
                    selectionKeys.clear();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class Client{
        public static void main(String[] args) {
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open();
                //同步
//                if (socketChannel.connect(new InetSocketAddress("localhost", 9999))){
//                    System.out.println("连接");
//                }
                //非阻塞模式
                socketChannel.configureBlocking(false);
                //非阻塞模式下连接未建立就返回了
                System.out.println(socketChannel.connect(new InetSocketAddress("localhost", 9999)));
                while (!socketChannel.finishConnect()){
                    System.out.println("连接");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
