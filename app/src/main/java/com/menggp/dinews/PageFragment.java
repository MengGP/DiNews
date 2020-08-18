package com.menggp.dinews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    // Номер страницы
    private int pageNumber;

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
        // Получаем разметку
        View result = inflater.inflate(R.layout.fragment_page, container, false);

        // Получаем вид с разметки
        TextView pageHeader = (TextView) result.findViewById(R.id.displayText);

        String header = String.format("Фрагмент %d", pageNumber+1);

        // устанавливаем данные на разметку
        pageHeader.setText(header);

        return result;
    }

    // Метод возвращает строку заголовка страницы ( с номером страницы )
    public static String getTitle(Context context, int position) {
        return "Page #" + String.valueOf(position+1);
    }

}
