package com.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesTest {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Files.copy(Paths.get("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FilesTest.java"),
                new FileOutputStream("G:\\workspace\\nio-demo\\src\\main\\resources\\b.txt"));

        System.out.println("is hidden: "
                + Files.isHidden(Paths.get("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FilesTest.java")));

        List<String> lines = Files.readAllLines(
                Paths.get("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FilesTest.java"),
                Charset.forName("utf-8"));
        System.out.println(lines);
        System.out.println("size: "
                + Files.size(Paths.get("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FilesTest.java")));

        List<String> poem = new ArrayList<>();
        poem.add("水晶潭底银鱼跃");
        poem.add("清徐风中碧杆横");
        Files.write(Paths.get("G:\\workspace\\nio-demo\\src\\main\\resources\\pome.txt"), poem, Charset.forName("gbk"));

        Files.list(Paths.get(".")).forEach(path -> System.out.println(path));

        Files.lines(Paths.get("G:\\workspace\\nio-demo\\src\\main\\java\\com\\example\\FilesTest.java"),
                Charset.forName("utf-8")).forEach(line -> System.out.println(line));

        FileStore fileStore = Files.getFileStore(Paths.get("j:"));
        System.out.println(fileStore.getTotalSpace());
        System.out.println(fileStore.getUsableSpace());
    }

}
