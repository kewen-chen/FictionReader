package com.anitworld.fictionreader.bean;

/**
 * Created by vision on 4/24/16.
 */

public class Fiction {
    private String fictionName;
    private String fictionImageUrl;
    private String fictionAuthor;
    private String fictionType;
    private String fictionDescription;
    private String fictionUrl;
    private String fictionTxtUrl;

    public Fiction(String fictionName, String fictionImageUrl, String fictionAuthor, String fictionType,
                   String fictionDescription, String fictionUrl, String fictionTxtUrl) {
        this.fictionName = fictionName;
        this.fictionImageUrl = fictionImageUrl;
        this.fictionAuthor = fictionAuthor;
        this.fictionType = fictionType;
        this.fictionDescription = fictionDescription;
        this.fictionUrl = fictionUrl;
        this.fictionTxtUrl = fictionTxtUrl;
    }

    public String getFictionName() {
        return fictionName;
    }

    public String getFictionImageUrl() {
        return fictionImageUrl;
    }

    public String getFictionAuthor() {
        return fictionAuthor;
    }

    public String getFictionType() {
        return fictionType;
    }

    public String getFictionDescription() {
        return fictionDescription;
    }

    public String getFictionUrl() {
        return fictionUrl;
    }

    public String getFictionTxtUrl() {
        return fictionTxtUrl;
    }

    @Override
    public String toString() {
        return "Fiction{" +
                "fictionName='" + fictionName + '\'' +
                ", fictionImageUrl='" + fictionImageUrl + '\'' +
                ", fictionAuthor='" + fictionAuthor + '\'' +
                ", fictionType='" + fictionType + '\'' +
                ", fictionDescription='" + fictionDescription + '\'' +
                ", fictionUrl='" + fictionUrl + '\'' +
                ", fictionTxtUrl='" + fictionTxtUrl + '\'' +
                '}';
    }
}