package com.menggp.dinews.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.menggp.dinews.MainActivity;
import com.menggp.dinews.PageFragment;
import com.menggp.dinews.repository.DatabaseAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    private static final String LOG_TAG = "PageAdapter";
    private static final int MAX_PAGE = 5;

    private  Context context = null;
    DatabaseAdapter dbAdapter;

    public PageAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
        dbAdapter = new DatabaseAdapter( context );
    }

    // Возвращает количесво страниц - для ViewPager
    @Override
    public int getCount() {
        return MAX_PAGE;
    }

    // По номеру страницы - возвращает объкт фрагмента (с помошью фбричного метода PageFragment.newInstance() )
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // КЭШируем данные с новой ссылки в БД - если их там нет
        int currPage = position+1;

        if ( dbAdapter.getArtCount( currPage ) == 0  ) {
            dbAdapter.loadNewsCache(MainActivity.NEWS_SOURCE, currPage );
        }

        return PageFragment.newInstance(position);
    }

    // Установка заголовка страницы
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PageFragment.getTitle(context, position);
    }


}
