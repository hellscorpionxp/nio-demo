package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ReadFileTest {

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(
                "G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\ReadFileTest.java");
                FileChannel fcin = fis.getChannel();) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
            while (fcin.read(byteBuffer) != -1) {
                byteBuffer.flip();
                Charset charset = Charset.forName("GBK");
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(byteBuffer);
                System.out.println(charBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
