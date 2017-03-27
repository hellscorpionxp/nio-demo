package com.example;

import java.nio.charset.Charset;
import java.util.SortedMap;

public class CharsetTest {

    public static void main(String[] args) {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        for (String alias : map.keySet()) {
            System.out.println(map.get(alias));
        }
    }

}
