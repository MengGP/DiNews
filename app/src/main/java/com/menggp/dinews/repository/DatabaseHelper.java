package com.menggp.dinews.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
    Класс репозитория - для работы с SQLite
        - содержит описание БД
        - реализует доступ к БД
        - реализует создание и обновление схемы БД
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "newscache.db";  // имя БД
    private static final int SCHEMA = 1;                   // версия БД

    // Описание таблицы NewsCache
    public static final String TAB_NEWS_CACHE = "news_cache";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_IMG_URL = "img_url";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE = "date";
    public static final String COL_URL = "url";
    public static final String COL_SET_NUM = "set_num";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
    }

    // Создание БД - если не существует или сменилась версия ( запуск из onUpgrade )
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TAB_NEWS_CACHE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_IMG_URL + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_URL + " TEXT, " +
                COL_SET_NUM + " INTEGER ); "
        );
    }

    // Метод проверяет версию БД - если версия отличается от текущей - обновляем БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TAB_NEWS_CACHE);
        onCreate(db);
    }

}
