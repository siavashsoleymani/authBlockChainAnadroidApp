package com.example.qrcodereader.model;

public class LidDTO {
    private String lid;
    private String key;

    public LidDTO() {
    }

    public LidDTO(String lid, String key) {
        this.lid = lid;
        this.key = key;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
