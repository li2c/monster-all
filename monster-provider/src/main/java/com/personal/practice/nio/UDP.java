package com.personal.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class UDP {
    public static void main(String[] args) {
        DatagramChannel channel = null;
        try {
            channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(9999));
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            channel.receive(buf);
            System.out.println(new String(buf.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class Client{
        public static void main(String[] args) {

            String newData = "New String to write to file..." + System.currentTimeMillis();
            DatagramChannel channel = null;
            try {
                channel = DatagramChannel.open();
                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put(newData.getBytes());
                buf.flip();

                int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
