package com.menggp.dinews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.menggp.dinews.adapters.PageAdapter;
import com.menggp.dinews.repository.DatabaseAdapter;

/*
    Основная активити прложения:
        - при "холодном пуске приложения" КЭШируем перевую порцию новостей в БД - в это время отображаем сплэш скрин
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    public static final String SHOW_NEWS_ACTIVITY = "com.menggp.dinews.SHOW_NEWS_ACTIVITY";
    public static final String NEWS_URL_KEY = "news_url";

    public static final String NEWS_SOURCE = "https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=";
    private static final int NEWS_1 = 1;
    private static final int NEWS_2 = 2;
    private static final int NEWS_3 = 3;
    private static final int NEWS_4 = 4;
    private static final int NEWS_5 = 5;

    DatabaseAdapter dbAdapter;
    PageAdapter pageAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        // Подключем БД
        dbAdapter = new DatabaseAdapter( this );

        // Проверка "холодного" запуска приложения
        if ( savedInstanceState == null ) {
            // при холодном запуске - очищаем таблицу БД с КЭШем новостей и пробуем загрузить новости с первой ссылки
            dbAdapter.resetNewsCache();
            dbAdapter.loadNewsCache(NEWS_SOURCE, NEWS_1 );
            Log.d(LOG_TAG, " --- --- ---> Cold start ");
        }

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager)findViewById(R.id.pager);
        pageAdapter = new PageAdapter(
                this,
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter( pageAdapter );

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                updNewsCache(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    // Метод обновления
    public void onClickUpdateNewsList(View view) {
        updNewsCache(pager.getCurrentItem() + 1 );
    }

    // Обновление дынных в КЭШе и на странице - после восстановления связи с интернетом
    private void updNewsCache(int pageNumber) {
        dbAdapter.loadNewsCache(NEWS_SOURCE, pageNumber );
        if ( dbAdapter.getArtCount(pageNumber) > 0 ) {
            pageAdapter.notifyDataSetChanged();
            recreate();
        }
    }


}