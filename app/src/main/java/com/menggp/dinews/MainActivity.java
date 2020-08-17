package com.menggp.dinews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.menggp.dinews.datamodel.Article;
import com.menggp.dinews.datamodel.NewsList;
import com.menggp.dinews.services.JSONHelper;
import com.menggp.dinews.services.dataDownloader;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    NewsList newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState){



        // Скачиваем данные с удаленного узла в формате JSON
        dataDownloader dataDownloader = new dataDownloader();
        dataDownloader.execute("https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=1");

        // Десериализуем JSON в целевые данные
        try {
            newsList = JSONHelper.parceJSONNewsList(dataDownloader.get());
        } catch (InterruptedException | ExecutionException ex ) {
            Log.d(LOG_TAG, ex.getMessage());
        }

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( newsList != null ) {
            for (Article iter : newsList.getArticles() ) {
                Log.d(LOG_TAG, " ---> " + iter.getTitle() );
            }
        }


    }

}