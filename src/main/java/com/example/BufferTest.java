package com.example;

import java.nio.CharBuffer;

public class BufferTest {

    public static void main(String[] args) {
        CharBuffer charBuffer = CharBuffer.allocate(8);
        System.out.println("capacity: " + charBuffer.capacity());
        System.out.println("limit: " + charBuffer.limit());
        System.out.println("position: " + charBuffer.position());

        charBuffer.put('a');
        charBuffer.put('b');
        charBuffer.put('c');
        System.out.println("position: " + charBuffer.position());

        charBuffer.flip();
        System.out.println("position: " + charBuffer.position() + "; limit: " + charBuffer.limit());

        System.out.println("first: " + charBuffer.get() + "; position: " + charBuffer.position());

        charBuffer.clear();
        System.out.println("position: " + charBuffer.position() + "; limit: " + charBuffer.limit());
        System.out.println("three: " + charBuffer.get(2));
        System.out.println("position: " + charBuffer.position());
    }

}
