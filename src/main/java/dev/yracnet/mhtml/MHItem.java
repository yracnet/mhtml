/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

import java.util.Base64;
import java.util.List;

/**
 *
 * @author wyujra
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString(exclude = {"content"})
public class MHItem {

    private String contentID;
    private String contentType;
    private String contentTransferEncoding;
    private String contentLocation;

    private List<String> content;

    public byte[] getContentBase64AsByteArray() {
        if (content == null) {
            return null;
        }
        String result = content.stream()
                .reduce("", (a, b) -> a + b);
        return Base64.getDecoder().decode(result);
    }

    public String getContentQuotedPrintableAsString() {
        if (content == null) {
            return null;
        }
        String result = this.content.stream()
                .reduce("", (a, b) -> a + b);
        return QuotedPrintable.encode(result, "UTF-8");
        
//        return content.stream()
//                .reduce("", (a, b) -> {
//                    if (b.length() == 0) {
//                        b = "\n";
//                    } else if (b.endsWith("=")) {
//                        b = b.substring(0, b.length() - 1);
//                    }
//                    return a + b;
//                });
    }
}
