package com.menggp.dinews.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.dinews.datamodel.Article;

public class SQLiteQueryHandler {

    // удаление данных news_cache
    static void resetNewsCache(SQLiteDatabase db) {
        db.execSQL( "DELETE FROM " + DatabaseHelper.TAB_NEWS_CACHE );
    }

    // добалвение записи article в таблицу news_cache
    static long insertArticle(SQLiteDatabase db, Article article) {
        // Создаем объект content values - для передачи в БД
        ContentValues cv = new ContentValues();

        cv.put( DatabaseHelper.COL_TITLE, article.getTitle() );
        cv.put( DatabaseHelper.COL_IMG_URL, article.getUrlToImage() );
        cv.put( DatabaseHelper.COL_DESCRIPTION, article.getDescription() );
        cv.put( DatabaseHelper.COL_DATE, article.getPublishedAt() );
        cv.put( DatabaseHelper.COL_URL, article.getUrl() );
        cv.put( DatabaseHelper.COL_SET_NUM, article.getSetNum() );

        return db.insert(
                DatabaseHelper.TAB_NEWS_CACHE,
                null,
                cv
        );
    }

    // возвращает все записи таблицы news_cache в виде курсора
    static Cursor getAllArticles(SQLiteDatabase db) {
        // Массив строк - содержащий получаемые в запросе поля
        String[] columns = new String[] {
                DatabaseHelper.COL_ID,
                DatabaseHelper.COL_TITLE,
                DatabaseHelper.COL_IMG_URL,
                DatabaseHelper.COL_DESCRIPTION,
                DatabaseHelper.COL_DATE,
                DatabaseHelper.COL_URL,
                DatabaseHelper.COL_SET_NUM,
        };

        return db.query(
                DatabaseHelper.TAB_NEWS_CACHE,  // таблица
                columns,                        // поля
                null,                  // блок условий
                null,               // аргументы условий
                null,                   // блок группировки
                null,                    // блок HAVING
                null                    // блок сортировки
        );

    }

}
