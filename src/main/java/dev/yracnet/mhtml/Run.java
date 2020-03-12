/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

import java.io.File;

/**
 *
 * @author wyujra
 */
public class Run {

    public static void main(String[] args) {
        File basedir = new File("/home/wyujra/Downloads/java_the_complete_reference__9th_edition");
        File output = new File("/home/wyujra/Downloads/out");
        output.mkdirs();
        //Stream.of(basedir.listFiles()).forEach(it -> processFile(it, output));
        processFile(basedir.listFiles()[0], output);

    }

    private static void processFile(File file, File output) {
        try {
            MHRoot root = MHFactory.createMHRoot(file);
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
