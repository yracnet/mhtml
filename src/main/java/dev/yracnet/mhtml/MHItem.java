/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.mhtml;

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

}
