package com.personal.practice.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 单从这里来看实际上与多路复用I/O没有任何联系。
 */
public class FileChannelControl {
    public static void main(String[] args) {
        File source=new File("nio.txt");
        System.out.println(source.getAbsoluteFile());
        RandomAccessFile file= null;
        try {
            file = new RandomAccessFile(source,"rw");
            FileChannel fileChannel=file.getChannel();
            ByteBuffer buffer=ByteBuffer.allocate(128);
            int readBytes=fileChannel.read(buffer);
            System.out.println("从文件中读取了"+readBytes+"字节");
            System.out.println("读模式下的buffer"+buffer);
            System.out.println("读取后文件流的pos为"+fileChannel.position());

            String s=new String(buffer.array());
            System.out.println(s);
            //读模式转写模式
            //这里如果不转换模式的话limit不会变成已读，会直接将pos=16写到limit=128，
            //buffer操作的永远是pos到limit之间的数据
            buffer.flip();
            System.out.println("转换读写模式瘦的buffer:"+buffer);
            //随着写操作 随机流的文件读写指针也指到了最后
            fileChannel.write(buffer);
            System.out.println("执行完写操作之后的buffer"+buffer);
            System.out.println("执行完写操作之后的随机读写流"+fileChannel.position());
            buffer.clear();
            System.out.println("clear之后的buffer"+buffer);
            //再次read的时候 因为读写指针在文件末位 所以返回-1
            System.out.println(fileChannel.read(buffer));;
            System.out.println("再次读之后的buffer"+buffer);
            System.out.println("再次读之后的随机读写流"+fileChannel.position());
            fileChannel.position(0);
            System.out.println(fileChannel.read(buffer));;
            System.out.println("再次读之后的buffer"+buffer);
            System.out.println("再次读之后的随机读写流"+fileChannel.position());
//            while (buffer.hasRemaining()){
//                System.out.print(buffer.getChar());
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
