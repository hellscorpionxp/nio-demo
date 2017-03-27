package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileChannelTest {

    public static void main(String[] args) {
        File file = new File("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FileChannelTest.java");
        try (FileChannel inChannel = new FileInputStream(file).getChannel();
                FileChannel outChannel = new FileOutputStream("G:\\workspace\\nio-demo\\src\\main\\resources\\a.txt")
                        .getChannel();) {
            MappedByteBuffer byteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            outChannel.write(byteBuffer);
            byteBuffer.clear();
            Charset charset = Charset.forName("GBK");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(byteBuffer);
            System.out.println(charBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
