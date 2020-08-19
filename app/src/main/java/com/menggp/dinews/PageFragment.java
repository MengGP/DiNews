package com.menggp.dinews;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result;
        dbAdapter = new DatabaseAdapter( inflater.getContext() );

        // Проверяем - есть ли данные в КЭШе для открывамой страницы
        if ( dbAdapter.getArtCount(pageNumber+1)>0  ) {
            // Получаем разметку
            result = inflater.inflate(R.layout.fragment_page, container, false);

            // Получаем вид с разметки
            TextView pageHeader = (TextView) result.findViewById(R.id.displayText);

            String header = "dbAdapter.getArtCount(pageNumber) = \n" + dbAdapter.getArtCount(pageNumber + 1);



            // устанавливаем данные на разметку
            pageHeader.setText(header);
        } else {
            // Получаем разметку
            result = inflater.inflate(R.layout.fragment_page, container, false);

            // Получаем вид с разметки
            TextView pageHeader = (TextView) result.findViewById(R.id.displayText);

            String header = "Have no data : " + pageNumber;

            // устанавливаем данные на разметку
            pageHeader.setText(header);


        }

        return result;
    }

    // Метод возвращает строку заголовка страницы ( с номером страницы )
    public static String getTitle(Context context, int position) {
        return "Страница №" + String.valueOf(position+1);
    }

}
