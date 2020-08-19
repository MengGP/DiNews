package com.menggp.dinews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.menggp.dinews.adapters.PageAdapter;
import com.menggp.dinews.repository.DatabaseAdapter;

/*
    Основная активити прложения:
        - при "холодном пуске приложения" КЭШируем перевую порцию новостей в БД - в это время отображаем сплэш скрин
        - далее работаем с элементом ViewPager - как с основным отображаемым элеметтом
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    // --- Публичные константы приложения
    public static final String SHOW_NEWS_ACTIVITY = "com.menggp.dinews.SHOW_NEWS_ACTIVITY";
    public static final String SHOW_INFO_ACTIVITY = "com.menggp.dinews.SHOW_INFO_ACTIVITY";
    public static final String NEWS_URL_KEY = "news_url";
    public static final String PAGE_NUM_KEY = "page_num";
    public static final String NEWS_SOURCE = "https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=";

    private static final int NEWS_1 = 1;

    private DatabaseAdapter dbAdapter;
    private PageAdapter pageAdapter;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        // Подключем БД
        dbAdapter = new DatabaseAdapter( this );

        // Проверка "холодного" запуска приложения
        if ( savedInstanceState == null ) {
            // при холодном запуске - очищаем таблицу БД с КЭШем новостей и пробуем загрузить новости с первой ссылки
            dbAdapter.resetNewsCache();
            dbAdapter.loadNewsCache(NEWS_SOURCE, NEWS_1 );
        }

        // установка основной темы - вместо сполэш-скрина и загрузка разметки MainActivity
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получем ViewPager c разметки
        pager = (ViewPager)findViewById(R.id.pager);

        // Создаем и устанавливаем адаптер PagerAdapter для ViewPager
        pageAdapter = new PageAdapter(
                this,
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter( pageAdapter );

        // Слушатель смены страницы
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0
                ,1
                ,0
                ,"info")
                .setIcon(R.drawable.about_program)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    } // end_method

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case 1:
                Intent intentSettings = new Intent(SHOW_INFO_ACTIVITY);
                startActivity(intentSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    } // end_method

    // Метод отбработки нажатия на кнопку обновления
    public void onClickUpdateNewsList(View view) {
        updNewsCache(pager.getCurrentItem() + 1 );
    }

    // Обновление дынных в КЭШе и на странице, вызывается
    //      - при смене страницы
    //      - при нажатии на кнопку "обновить" на странице заглушке
    private void updNewsCache(int pageNumber) {
        if ( dbAdapter.getArtCount(pageNumber) == 0 ) {
            dbAdapter.loadNewsCache(NEWS_SOURCE, pageNumber);
            if (dbAdapter.getArtCount(pageNumber) > 0) {
                pageAdapter.notifyDataSetChanged();
                recreate();
            }
        }
    }

}