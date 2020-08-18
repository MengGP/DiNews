package com.menggp.dinews.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.menggp.dinews.PageFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private  Context context = null;

    public PageAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
    }

    // Возвращает количесво страниц - для ViewPager
    @Override
    public int getCount() {
        return 10;
    }

    // По номеру страницы - возвращает объкт фрагмента (с помошью фбричного метода PageFragment.newInstance() )
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    // Установка заголовка страницы
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PageFragment.getTitle(context, position);
    }
}
