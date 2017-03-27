package com.example;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetTransformTest {

    public static void main(String[] args) throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer cb = CharBuffer.allocate(8);
        cb.put('孙');
        cb.put('悟');
        cb.put('空');
        cb.flip();

        ByteBuffer bb = encoder.encode(cb);
        for (int i = 0; i < bb.capacity(); i++) {
            System.out.print(bb.get(i) + "  ");
        }
        System.out.println("\n" + decoder.decode(bb));
    }

}
