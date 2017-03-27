package com.example;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorTest {

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("g:", "workspace", "nio-demo"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("now: " + file + " f");
                if (file.endsWith("FileVisitorTest.java")) {
                    System.out.println("find");
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("now: " + dir + " d");
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
