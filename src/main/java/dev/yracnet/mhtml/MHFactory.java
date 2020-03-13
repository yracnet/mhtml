/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wyujra
 */
public class MHFactory {

    private static final String regex = "(^[A-Z][A-Za-z-]*:) ((.|;|\\n\\s)*)$";

    private static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    public static MHRoot createMHRoot(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        MHRoot root = new MHRoot();
        root.setFile(file);
        root.setReader(reader);

        String block = readBlock(reader);
        Matcher matcher = pattern.matcher(block);
        while (matcher.find()) {
            String name = matcher.group(1);
            String value = matcher.group(2);
            if ("From:".equals(name)) {
                root.setFrom(value);
            } else if ("Snapshot-Content-Location:".equals(name)) {
                root.setSnapshotContentLocation(value);
            } else if ("Subject:".equals(name)) {
                root.setSubject(value);
            } else if ("Date:".equals(name)) {
                root.setDate(value);
            } else if ("Content-Type:".equals(name)) {
                root.setContentType(value);
            } else if ("MIME-Version:".equals(name)) {
                root.setMimeVersion(value);
            } else {
                System.out.println("---> ?" + name + " - " + value);
            }
        }
        String boundary = root.getBoundary();
        //read fisrt blank block
        readBoundaryBlock(reader, boundary);
        //start------------
        List<String> content;
        block = readBlock(reader);
        while (block.length() > 10) {
            matcher = pattern.matcher(block);
            MHItem item = new MHItem();
            while (matcher.find()) {
                String name = matcher.group(1);
                String value = matcher.group(2);
                if ("Content-ID:".equals(name)) {
                    item.setContentID(value);
                } else if ("Content-Transfer-Encoding:".equals(name)) {
                    item.setContentTransferEncoding(value);
                } else if ("Content-Location:".equals(name)) {
                    item.setContentLocation(value);
                } else if ("Content-Type:".equals(name)) {
                    item.setContentType(value);
                } else {
                    System.out.println("---> ?" + name + " - " + value);
                }
            }
            content = readBoundaryBlock(reader, boundary);
            item.setContent(content);
            root.addItem(item);
            block = readBlock(reader);
        };
        return root;
    }

    public static void nextBoundaryBlock(BufferedReader reader, String boundary) throws IOException {
        String line = null;
        do {
            line = reader.readLine();
        } while (line != null && !line.equals(boundary));
    }

    public static List<String> readBoundaryBlock(BufferedReader reader, String boundary) throws IOException {
        List<String> content = new ArrayList<>();
        String line = reader.readLine();
        while (line != null && !line.equals(boundary)) {
            content.add(line);
            line = reader.readLine();
        }
        return content;
    }

    public static String readBlock(BufferedReader reader) throws IOException {
        return readBlock(reader, a -> {
        });
    }

    public static String readBlock(BufferedReader reader, Consumer<String> invoke) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null && line.length() > 0) {
            invoke.accept(line);
            sb.append(line).append("\n");
            line = reader.readLine();
        }
        return sb.toString();
    }
    
}
