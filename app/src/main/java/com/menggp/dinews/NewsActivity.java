package com.menggp.dinews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
    Активити новости
        - отображает новость в элементе WebView
 */
public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Настройка ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Просмотр новости");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Получем данные из предыдущей Activity
        String newsUrl="";
        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            newsUrl = extras.getString(MainActivity.NEWS_URL_KEY);
        }

        // Получаем элементы разметки
        WebView webViewBrowser = (WebView)findViewById(R.id.web_view_browser);

        // Настраиваем и передаем ссылку для WebView
        webViewBrowser.getSettings().setJavaScriptEnabled(true);
        webViewBrowser.setWebViewClient( new WebViewClient() );
        webViewBrowser.loadUrl(newsUrl);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home :
                goMainActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Метод - возвращает на MAIN_ACTIVITY
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    } // end_method

}