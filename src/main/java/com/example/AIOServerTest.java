package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServerTest {

    private static final int PORT = 30000;
    private static final String UTF8 = "utf-8";
    private static List<AsynchronousSocketChannel> channelList = new ArrayList<>();

    public void startListen() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup)
                .bind(new InetSocketAddress(PORT));
        serverChannel.accept(null, new AcceptHandler(serverChannel));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
        private AsynchronousServerSocketChannel serverChannel;
        private ByteBuffer buff = ByteBuffer.allocate(1024);

        public AcceptHandler(AsynchronousServerSocketChannel serverChannel) {
            this.serverChannel = serverChannel;
        }

        @Override
        public void completed(AsynchronousSocketChannel sc, Object attachment) {
            AIOServerTest.channelList.add(sc);
            serverChannel.accept(null, this);
            sc.read(buff, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    buff.flip();
                    String content = StandardCharsets.UTF_8.decode(buff).toString();
                    for (AsynchronousSocketChannel c : AIOServerTest.channelList) {
                        try {
                            c.write(ByteBuffer.wrap(content.getBytes(AIOServerTest.UTF8))).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    buff.clear();
                    sc.read(buff, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("read fail: " + exc);
                    AIOServerTest.channelList.remove(sc);
                }
            });
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("connect fail: " + exc);
        }
    }

    public static void main(String[] args) throws IOException {
        new AIOServerTest().startListen();
    }

}
