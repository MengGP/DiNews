package com.menggp.dinews.datamodel;

import java.util.List;

/*
    Класс - описывает список статей
        - используется для удобства десериализации входных данных JSON
 */
public class NewsList {

    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
