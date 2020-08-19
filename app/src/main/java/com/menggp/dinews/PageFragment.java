package com.menggp.dinews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.menggp.dinews.adapters.ArticlesAdapter;
import com.menggp.dinews.datamodel.Article;
import com.menggp.dinews.repository.DatabaseAdapter;

import java.util.List;

public class PageFragment extends Fragment {

    private static final String LOG_TAG = "PageFragment";

    // Номер страницы
    private int pageNumber;
    DatabaseAdapter dbAdapter;

    // конструктор фрагмента
    public PageFragment() {
    }

    // Фабричный метод
    public static PageFragment newInstance(int page) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num")+1 : 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        ListView newsListView;
        List<Article> articles;
        ArticlesAdapter articlesAdapter;
        dbAdapter = new DatabaseAdapter( inflater.getContext() );

        // Проверяем - есть ли данные в КЭШе для открывамой страницы
        if ( dbAdapter.getArtCount(pageNumber)>0  ) {
            // Если в КЭШе есть данные

            // Получаем разметку
            view = inflater.inflate(R.layout.fragment_page_list, container, false);
            // Получаем элементы с разметки
            newsListView = (ListView)view.findViewById(R.id.news_list);
            articles = dbAdapter.getArticles(pageNumber);

            // Создаем адаптер для списка новостей на странице
            articlesAdapter = new ArticlesAdapter(
                    inflater.getContext(),
                    R.layout.article_list_item,
                    articles
            );
            // Устанавливаем адаптер для вида
            newsListView.setAdapter(articlesAdapter);


        } else {
            // Если КЭШ новостей пустой

            // Получаем разметку
            view = inflater.inflate(R.layout.fragment_page_empty, container, false);
        }

        return view;
    }

    // Метод возвращает строку заголовка страницы ( с номером страницы )
    public static String getTitle(Context context, int position) {
        return "Страница #" + String.valueOf(position+1);
    }

}
