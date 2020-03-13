/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 *
 * @author wyujra
 */
public class MHRun {

    public static void main(String[] args) {
        File basedir = new File("/home/wyujra/Downloads/java_the_complete_reference__9th_edition");
        File output = new File("/home/wyujra/Downloads/out");
        //Stream.of(basedir.listFiles()).forEach(it -> processFile(it, output));
        processFile(basedir.listFiles()[0], output);

    }

    private static void processFile(File file, File output) {
        try {
            output.mkdirs();
            MHRoot root = MHFactory.createMHRoot(file);
            System.out.println("FILE-->" + root.getSnapshotContentLocation());

            root.forEachMhItem(item -> {
                saveMhfile(item, output);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void saveMhfile(MHItem item, File output) {
        String location = item.getContentLocation();
        if (location == null || !location.startsWith("http")) {
            //skip
            return;
        }
        String type = item.getContentType();
        String encode = item.getContentTransferEncoding();
        File file = createFile(output, location);
        System.out.println("ITEM--> " + location + " - " + type + " - " + encode + " -->" + file);
        if (type.startsWith("image/") && "base64".equals(encode)) {
            writeContent(file, item.getContentBase64AsByteArray());
        }
        if (type.startsWith("text/") && "quoted-printable".equals(encode)) {
            writeContent(file, item.getContentQuotedPrintableAsString());
        }
        

    }

    private static File createFile(File output, String location) {
        location = location.replace("://", "_");
        location = location.replace(":", "_");
        File result = new File(output, location);
        if (result.exists()) {
            result.delete();
        }
        File parent = result.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return result;
    }

    private static void writeContent(File file, String content) {
        writeContent(file, content.getBytes());
    }

    private static void writeContent(File file, byte[] content) {
        try {
            Files.write(Paths.get(file.getPath()), content);
        } catch (Exception e) {
            System.out.println("Error: " + file + " - " + e.getMessage());
        }
    }
}
