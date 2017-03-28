package com.example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class AIOClientTest {

    private static final int PORT = 30000;
    private static final String UTF8 = "utf-8";
    private AsynchronousSocketChannel clientChannel;
    private JFrame mainWin = new JFrame("chat");
    private JTextArea textArea = new JTextArea(16, 48);
    private JTextField textField = new JTextField(40);
    private JButton sendBn = new JButton("send");

    public void init() {
        mainWin.setLayout(new BorderLayout());
        textArea.setEditable(false);
        mainWin.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel jp = new JPanel();
        jp.add(textField);
        jp.add(sendBn);
        Action sendAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText();
                if (content.trim().length() > 0) {
                    try {
                        clientChannel.write(ByteBuffer.wrap(content.trim().getBytes(UTF8))).get();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                textField.setText("");
            }
        };
        sendBn.addActionListener(sendAction);
        textField.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_MASK), "send");
        textField.getActionMap().put("send", sendAction);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.add(jp, BorderLayout.SOUTH);
        mainWin.pack();
        mainWin.setVisible(true);
    }

    public void connect() throws IOException, InterruptedException, ExecutionException {
        final ByteBuffer buff = ByteBuffer.allocate(1024);
        ExecutorService executor = Executors.newFixedThreadPool(80);
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        clientChannel = AsynchronousSocketChannel.open(channelGroup);
        clientChannel.connect(new InetSocketAddress("127.0.0.1", PORT)).get();
        textArea.append("---connect sucess---\n");
        buff.clear();
        clientChannel.read(buff, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                buff.flip();
                String content = StandardCharsets.UTF_8.decode(buff).toString();
                textArea.append("say: " + content + "\n");
                buff.clear();
                clientChannel.read(buff, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("read fail: " + exc);
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        AIOClientTest client = new AIOClientTest();
        client.init();
        client.connect();
    }

}
