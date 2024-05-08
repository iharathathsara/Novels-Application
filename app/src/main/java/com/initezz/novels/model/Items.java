package com.initezz.novels.model;

public class Items {
    private String id;
    private String title;
    private String description;
    private String author;
    private String pdfpath;
    private String image;

    public Items() {
    }

    public Items(String id, String title, String description, String author, String pdfpath, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.pdfpath = pdfpath;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
