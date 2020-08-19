package com.menggp.dinews.datamodel;

import androidx.annotation.NonNull;

/*
    Класс - описывает одину статью (новость)
 */
public class Article {

    private long id;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private int setNum;

    public Article(long id, String title, String urlToImage, String description, String publishedAt, String url, int setNum) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.setNum = setNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }

    @NonNull
    @Override
    public String toString() {
        return "Article id = " + id + "\n" +
                "title : " + title +  "\n" +
                "imgURL : " + urlToImage + "\n" +
                "description : " + description + "\n" +
                "date : " + publishedAt + "\n" +
                "url : " + url + "\n" +
                "set_num : " + setNum;

    }
}
