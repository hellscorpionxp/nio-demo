package com.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Date;
import java.util.List;

public class AttributeViewTest {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("G:\\workspace\\nio-demo\\src\\main\\resources\\b.txt");

        BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes basicAttribs = basicView.readAttributes();
        System.out.println(new Date(basicAttribs.creationTime().toMillis()));
        System.out.println(new Date(basicAttribs.lastAccessTime().toMillis()));
        System.out.println(new Date(basicAttribs.lastModifiedTime().toMillis()));
        System.out.println(basicAttribs.size());

        FileOwnerAttributeView ownerView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        System.out.println(ownerView.getOwner());
        UserPrincipal user = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("guest");
        // ownerView.setOwner(user);

        UserDefinedFileAttributeView userView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        List<String> attrNames = userView.list();
        for (String name : attrNames) {
            ByteBuffer bb = ByteBuffer.allocate(userView.size(name));
            userView.read(name, bb);
            bb.flip();
            String value = Charset.defaultCharset().decode(bb).toString();
            System.out.println(name + "   " + value);
        }
        userView.write("abc", Charset.defaultCharset().encode("xyz"));

        DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);
        // dosView.setHidden(true);
        // dosView.setReadOnly(true);
    }

}
