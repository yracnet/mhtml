/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wyujra
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString(exclude = {"file", "reader", "mhfile"})
public class MHRoot {

    private File file;
    private BufferedReader reader;
    private String from;
    private String snapshotContentLocation;
    private String subject;
    private String date;
    private String mimeVersion;
    private String contentType;
    private String boundary;
    private List<MHItem> mhfile = new ArrayList<>();;

    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (contentType != null && contentType.contains("boundary=")) {
            int i = contentType.indexOf("boundary=") + 10;
            boundary = contentType.substring(i);
            i = boundary.indexOf("----", 5) + 4;
            boundary = "--" + boundary.substring(0, i);
        }
    }

    public void addItem(MHItem item) {
        mhfile.add(item);
    }

}
