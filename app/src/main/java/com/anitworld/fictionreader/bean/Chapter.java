package com.anitworld.fictionreader.bean;

/**
 * Created by vision on 4/24/16.
 */
public class Chapter {
    private String chapterName;
    private String chapterUrl;

    public Chapter(String chapterName, String chapterUrl) {
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterName='" + chapterName + '\'' +
                ", chapterUrl='" + chapterUrl + '\'' +
                '}';
    }
}
