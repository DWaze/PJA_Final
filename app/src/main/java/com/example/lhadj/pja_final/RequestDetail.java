package com.example.lhadj.pja_final;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lhadj on 12/25/2016.
 */
public class RequestDetail implements Serializable {

    private String ClientName ;
    private ArrayList<ListFile> files ;
    private String ownerIp ;
    private String destIp ;
    private int query ;
    String fileToDownload ;

    public RequestDetail(String clientName, ArrayList<ListFile> files, String ownerIp, String destIp, int query, String fileToDownload) {
        ClientName = clientName;
        this.files = files;
        this.ownerIp = ownerIp;
        this.destIp = destIp;
        this.query = query;
        this.fileToDownload = fileToDownload;
    }

    public String getClientName() {
        return ClientName;
    }

    public ArrayList<ListFile> getFiles() {
        return files;
    }

    public String getOwnerIp() {
        return ownerIp;
    }

    public String getDestIp() {
        return destIp;
    }

    public int getQuery() {
        return query;
    }

    public String getFileToDownload() {
        return fileToDownload;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setFiles(ArrayList<ListFile> files) {
        this.files = files;
    }

    public void setOwnerIp(String ownerIp) {
        this.ownerIp = ownerIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public void setQuery(int query) {
        this.query = query;
    }

    public void setFileToDownload(String fileToDownload) {
        this.fileToDownload = fileToDownload;
    }
}
