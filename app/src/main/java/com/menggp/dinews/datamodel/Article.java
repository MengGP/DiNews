package com.menggp.dinews.datamodel;

import androidx.annotation.NonNull;

import java.net.URL;

/*
    Класс - описывает одину статью
 */
public class Article {

    /*
        Внутренний класс - описывает обхъек класса Source - с 2я полями

    private class Source {
        private String id;
        private String name;

        public Source(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
     */

//    private Source source;
//    private String author;
    private long id;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
//    private String content;
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

    /*
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

        public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
     */

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
