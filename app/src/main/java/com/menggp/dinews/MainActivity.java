package com.menggp.dinews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.menggp.dinews.datamodel.Article;
import com.menggp.dinews.datamodel.NewsList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=1");
        // downloadData.execute("https://rawgit.com/startandroid/data/master/messages/messages1.json");



    }

    // приватный внутренний класс - скачивает
    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();

            NewsList newsList = gson.fromJson(s, NewsList.class);

            for (Article iter : newsList.getArticles()) {
                System.out.println(iter.getTitle());
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            String content="";
            try{
                content = downloadJSON(strings[0]);
            }
            catch (IOException ex){
                Log.e(TAG, "downloadXML: IO Exception reading data: " + ex.getMessage());
            }
            return content;
        }

        private String downloadJSON(String urlPath) throws IOException {
            StringBuilder JSONResult = new StringBuilder();
            BufferedReader reader = null;
            try {
                URL url = new URL(urlPath);
                //HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line=null;
                while ((line=reader.readLine()) != null) {
                    JSONResult.append(line);
                }
                return JSONResult.toString();
            } catch(MalformedURLException ex) {
                Log.e(TAG, "downloadJSON: Invalid URL " + ex.getMessage());
            } catch(IOException ex) {
                Log.e(TAG, "downloadJSON: IO Exception reading data: " + ex.getMessage());
            } catch(SecurityException ex) {
                Log.e(TAG, "downloadJSON: Security Exception.  Needs permisson? " + ex.getMessage());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
            return null;
        }
    }

}