package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockTest {

    public static void main(String[] args) {
        try (FileChannel channel = new FileOutputStream("G:\\workspace\\nio-demo\\src\\main\\resources\\a.txt")
                .getChannel();) {
            FileLock lock = channel.tryLock();
            Thread.sleep(10000);
            lock.release();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
