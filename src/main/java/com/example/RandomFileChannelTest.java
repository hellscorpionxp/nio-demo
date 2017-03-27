package com.example;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomFileChannelTest {

    public static void main(String[] args) {
        File file = new File("G:\\workspace\\nio-demo\\src\\main\\resources\\a.txt");
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw"); FileChannel randomChannel = raf.getChannel();) {
            ByteBuffer buffer = randomChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            randomChannel.position(file.length());
            randomChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
