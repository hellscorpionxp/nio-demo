package com.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class WatchServiceTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("c:").register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context() + "   " + event.kind());
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

}
