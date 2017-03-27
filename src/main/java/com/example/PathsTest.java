package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsTest {

    public static void main(String[] args) {
        Path path = Paths.get(".");
        System.out.println(path.getNameCount());

        Path absolutePath = path.toAbsolutePath();
        System.out.println(absolutePath);
        System.out.println(absolutePath.getRoot());
        System.out.println(absolutePath.getNameCount());

        Path path2 = Paths.get("j:", "publish", "codes");
        System.out.println(path2);
    }

}
