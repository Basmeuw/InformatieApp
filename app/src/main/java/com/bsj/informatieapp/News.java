package com.bsj.informatieapp;

public class News {
    private String imageFilename;
    private String title;
    private String link;
    private String source;

    public News(String imageFilename, String title, String link, String source) {
        this.imageFilename = imageFilename;
        this.title = title;
        this.link = link;
        this.source = source;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
