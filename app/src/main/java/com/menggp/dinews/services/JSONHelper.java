package com.menggp.dinews.services;

import com.google.gson.Gson;
import com.menggp.dinews.datamodel.NewsList;

/*
    Вспомогательный класс
        - реализует десириализацию из JSON
 */
public class JSONHelper {

    public static NewsList parseJSONNewsList(String jStr) {
        Gson gson = new Gson();
        return gson.fromJson(jStr, NewsList.class);
    }

}
