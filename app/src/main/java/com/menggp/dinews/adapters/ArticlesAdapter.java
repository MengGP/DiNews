package com.menggp.dinews.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.menggp.dinews.R;
import com.menggp.dinews.datamodel.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
    Адаптер списка новостей
        - отображестя на странице PageFragment
        - разметка элемента: article_list_item
 */
public class ArticlesAdapter extends ArrayAdapter<Article> {

    private static final String LOG_TAG = "ArticlesAdapter";

    private LayoutInflater inflater;
    private int layout;
    private List<Article> articles;

    public ArticlesAdapter(@NonNull Context context, int resource, List<Article> articles) {
        super(context, resource, articles);
        this.articles = articles;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if ( convertView==null ) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Получаем элемент списка
        Article articleItem = articles.get( position );

        // Раполняем разметку данными
            // Заголовок
        viewHolder.titleOnList.setText( articleItem.getTitle() );
            // Изображение
        viewHolder.imgUrlOnList.setImageResource(R.drawable.no_image);     // изображение-заглушка
        if ( articleItem.getUrlToImage() != null ) {
            if ( articleItem.getUrlToImage().length() > 0 ) {
                Picasso.get()
                        .load( articleItem.getUrlToImage() )
                        .resize(48, 48)
                        .error(R.drawable.no_image)
                        .into(viewHolder.imgUrlOnList);
            }
        }
            // Описание
        viewHolder.descriptionOnList.setText( articleItem.getDescription() );
            // Дата
        viewHolder.dateOnList.setText( articleItem.getPublishedAt() );

        return convertView;
    }

    // Приватный класс ViewHolder
    private static class ViewHolder {
        final TextView titleOnList;
        final ImageView imgUrlOnList;
        final TextView descriptionOnList;
        final TextView dateOnList;

        ViewHolder(View view) {
            titleOnList = (TextView)view.findViewById(R.id.title);
            imgUrlOnList = (ImageView) view.findViewById(R.id.img_url);
            descriptionOnList = (TextView)view.findViewById(R.id.description);
            dateOnList = (TextView)view.findViewById(R.id.date);
        }
    }

}
;