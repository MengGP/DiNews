package com.menggp.dinews.datamodel;

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
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
//    private String content;

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


}
