package com.menggp.dinews.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.menggp.dinews.datamodel.Article;

public class SQLiteQueryHandler {

    // Массив строк - содержащий получаемые в запросе поля
    private static String[] columns = new String[] {
            DatabaseHelper.COL_ID,
            DatabaseHelper.COL_TITLE,
            DatabaseHelper.COL_IMG_URL,
            DatabaseHelper.COL_DESCRIPTION,
            "strftime('%Y-%m-%d',"+ DatabaseHelper.COL_DATE + ")",
            DatabaseHelper.COL_URL,
            DatabaseHelper.COL_SET_NUM,
    };

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

    // Возвращает новости по номеру набора из news_cache в виде курсора
    public static Cursor getArticles(SQLiteDatabase db, int setNum) {
        // Условие и агрументы условия
        String whereClause =  DatabaseHelper.COL_SET_NUM + " = ? ";
        String[] whereArgs = new String[] { String.valueOf(setNum) };

        return db.query(
                DatabaseHelper.TAB_NEWS_CACHE,  // таблица
                columns,                        // поля
                whereClause,                    // блок условий
                whereArgs,                      // аргументы условий
                null,                  // блок группировки
                null,                   // блок HAVING
                null                   // блок сортировки
        );
    }


    // Возвращает количество закештрованных новостей для указанного набора
    public static long getArtCount(SQLiteDatabase db, int setNum) {
        String whereClause =  DatabaseHelper.COL_SET_NUM + " = ? ";
        String[] whereArgs = new String[] { String.valueOf(setNum) };
        return DatabaseUtils.queryNumEntries(
                db,                             // БД
                DatabaseHelper.TAB_NEWS_CACHE,  // таблица
                whereClause,
                whereArgs
        );
    }
}
