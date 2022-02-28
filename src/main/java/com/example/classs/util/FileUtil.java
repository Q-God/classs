package com.example.classs.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil {

    /**
     * 读文件返回String
     *
     * @param path 文件路径
     * @return 读取内容
     */
    public static String readFile(String path) {
        String string = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(path, "rw");
            FileChannel channel = randomAccessFile.getChannel();

            ByteBuffer allocate = ByteBuffer.allocate(1024 << 4); //16KB缓冲区
            List<Byte> byteList = new ArrayList<>();
            byte[] array;
            int length;
            while ((length = channel.read(allocate)) != -1) {
                array = allocate.array();
                for (int i = 0; i < length; i++) {
                    byteList.add(array[i]);
                }
                allocate.clear();
            }
            channel.close();
            byte[] arr = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                arr[i] = byteList.get(i);
            }
            string = new String(arr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("readFile failure!! error={} message={}", e, e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    log.error("randomAccessFile.close() failure!! error={} message={}", e, e.getMessage());
                }
            }
        }
        return string;
    }

    /**
     * String写文件
     *
     * @param text 内容
     * @param path 目标文件路径
     */
    public static void writeFile(String text, String path) {
        RandomAccessFile randomAccessFile = null;
        try {
            Files.deleteIfExists(Paths.get(path)); //目标文件存在先删除
            randomAccessFile = new RandomAccessFile(path, "rw");
            FileChannel channel = randomAccessFile.getChannel();
            channel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));
            channel.close();
        } catch (Exception e) {
            log.error("writeFile failure!! error={} message={}", e, e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    log.error("randomAccessFile.close() failure!! error={} message={}", e, e.getMessage());
                }
            }
        }
    }

    public static File copyFile(String source, String target) throws IOException {
        //java.io.RandomAccessFile类，可以设置读、写模式的IO流类。
        //"r"表示：只读--输入流，只读就可以。
        RandomAccessFile randomAccessFile1 = null;
        RandomAccessFile randomAccessFile2 = null;


        try {
            randomAccessFile1 = new RandomAccessFile(source, "r");
            //"rw"表示：读、写--输出流，需要读、写。
            randomAccessFile2 = new RandomAccessFile(target, "rw");

            // 获得FileChannel管道对象
            FileChannel fileChannel1 = randomAccessFile1.getChannel();
            FileChannel fileChannel2 = randomAccessFile2.getChannel();

            // 获取文件的大小
            long size = fileChannel1.size();

            // 直接把硬盘中的文件映射到内存中
            MappedByteBuffer mappedByteBuffer1 = fileChannel1.map(FileChannel.MapMode.READ_ONLY, 0, size);
            MappedByteBuffer mappedByteBuffer2 = fileChannel2.map(FileChannel.MapMode.READ_WRITE, 0, size);

            // 循环读取数据
            for (long i = 0; i < size; i++) {
                // 读取字节
                byte byt = mappedByteBuffer1.get();
                // 保存到第二个数组中
                mappedByteBuffer2.put(byt);
            }
            // 释放资源
            fileChannel2.close();
            fileChannel1.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != randomAccessFile2) {
                try {
                    randomAccessFile2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != randomAccessFile1) {
                try {
                    randomAccessFile1.close();
                } catch (IOException e) {
                    log.error("randomAccessFile.close() failure!! error={} message={}", e, e.getMessage());
                }
            }
        }
        return new File(target);
    }


    /**
     * <p>使用IO拷贝文件</p>
     *
     * @param fromFile 被拷贝的文件
     * @param toFile   拷贝后的文件
     * @throws IOException
     */
    public static void copyFileByIO(String fromFile, String toFile) throws IOException {
        File inputFile = new File(fromFile);
        File outputFile = new File(toFile);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] bytes = new byte[1024];
        int c;
        while ((c = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, c);
        }
        inputStream.close();
        outputStream.close();
    }
}
