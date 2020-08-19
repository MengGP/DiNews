package com.menggp.dinews.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.menggp.dinews.datamodel.Article;
import com.menggp.dinews.datamodel.NewsList;
import com.menggp.dinews.services.DataDownloader;
import com.menggp.dinews.services.JSONHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DatabaseAdapter {

    private static final String LOG_TAG = "DatabaseAdapter";
    private static final int NO_SET_NUM = 0;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper( context.getApplicationContext() );
    }

    // Открытие соединения с БД
    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    // Закрытие соединения с БД
    private void close() {
        dbHelper.close();
    }

    // Метод стирает данные б таблице news_cache
    public void resetNewsCache() {
        this.open();
        SQLiteQueryHandler.resetNewsCache( db );
        this.close();
    }

    // метод загружает одну статью в БД
    public long insertArticle(Article article) {
        long result;
        this.open();
        result = SQLiteQueryHandler.insertArticle(db, article);
        this.close();
        return result;
    }

    // Метод возвращает заданный набор новостей из таблицы news_cache
    public List<Article> getArticles(int setNumClause) {
        return getArticlesGeneral(setNumClause);
    }

    // Метод возвращает все записи из таблицы news_cache
    public List<Article> getArticles() {
        return getArticlesGeneral(NO_SET_NUM);
    }

    // Метод возвращает записи из таблицы news_cache
    private List<Article> getArticlesGeneral(int setNumClause) {
        List<Article> articles = new ArrayList<>(); // результирующий список
        this.open();

        // Запрашиваем данные из БД в зависимости от условий
        Cursor cursor;
        if ( setNumClause != 0) cursor = SQLiteQueryHandler.getArticles(db, setNumClause);
        else cursor = SQLiteQueryHandler.getAllArticles(db);

        if ( cursor.moveToFirst() ) {
            do {
                long id = cursor.getLong( cursor.getColumnIndex( dbHelper.COL_ID ));
                String title = cursor.getString( cursor.getColumnIndex( dbHelper.COL_TITLE ) );
                String imgURL = cursor.getString( cursor.getColumnIndex( dbHelper.COL_IMG_URL ) );
                String description = cursor.getString( cursor.getColumnIndex( dbHelper.COL_DESCRIPTION ) );
                String date = cursor.getString( cursor.getColumnIndex( dbHelper.COL_DATE ) );
                String url = cursor.getString( cursor.getColumnIndex( dbHelper.COL_URL ) );
                int setNum = cursor.getInt( cursor.getColumnIndex( dbHelper.COL_SET_NUM) );

                articles.add( new Article(id, title,imgURL, description, date, url, setNum ));
            } while ( cursor.moveToNext() );
        }
        cursor.close();

        this.close();
        return articles;
    }

    // Возвращает количество закешированных новостей для указанного набора
    public long getArtCount(int setNum) {
        long count;
        this.open();
        count = SQLiteQueryHandler.getArtCount(db, setNum);
        this.close();
        return count;
    }

    // Метод загружает список новостей с переданного URL
    public void loadNewsCache(String url, int setNum) {
        // Скачиваем данные с удаленного узла в формате JSON
        DataDownloader dataDownloader = new DataDownloader();
        dataDownloader.execute( url+setNum );

        // Десериализуем JSON в целевые данные
        try {
            NewsList newsList = JSONHelper.parceJSONNewsList(dataDownloader.get());

            if ( newsList != null ) {
                // Обрабатываем статьи по одной
                for (Article article : newsList.getArticles()) {
                    // устанавливаем значение для параметра setNum
                    article.setSetNum( setNum );
                    // добавляем статью в БД
                    insertArticle(article);
                }
            } else
                Log.d(LOG_TAG, " Download data from remote host is unavailable ");


        } catch (InterruptedException | ExecutionException ex ) {
            Log.d(LOG_TAG, ex.getMessage());
        }
    }




}
