package com.menggp.dinews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.menggp.dinews.adapters.PageAdapter;
import com.menggp.dinews.datamodel.Article;
import com.menggp.dinews.datamodel.NewsList;
import com.menggp.dinews.repository.DatabaseAdapter;
import com.menggp.dinews.services.JSONHelper;
import com.menggp.dinews.services.DataDownloader;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
    Основная активити прложения:
        - при "холодном пуске приложения" КЭШируем перевую порцию новостей в БД - в это время отображаем сплэш скрин
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private static final String NEWS_SOURCE = "https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=";
    private static final int NEWS_1 = 1;

    DatabaseAdapter dbAdapter;
    NewsList newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        // Подключем БД
        dbAdapter = new DatabaseAdapter( this );

        // Проверка "холодного" запуска приложения
        if ( savedInstanceState == null ) {
            // при холодном запуске - очищаем таблицу БД с КЭШем новостей и пробуем загрузить новости
            dbAdapter.resetNewsCache();
            dbAdapter.loadNewCache(NEWS_SOURCE, NEWS_1 );
            Log.d(LOG_TAG, " --- --- ---> Cold start ");
        }

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PageAdapter(this, getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        // Проверка данных в БД
        // newsList.setArticles( dbAdapter.getArticles() );
        List<Article> arts =  dbAdapter.getArticles();
        for ( Article iter : arts ) {
            System.out.println(" --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- --- ");
            System.out.println( iter.toString() );
            System.out.println(" --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- ---  --- --- --- ");
        }


    }

    // При закрытии приложения - очищаем КЭШ новостей
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.resetNewsCache();
    }
}