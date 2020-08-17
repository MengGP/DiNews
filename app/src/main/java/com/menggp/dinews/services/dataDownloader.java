package com.menggp.dinews.services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
    Класс реализует скачивание данных - по протоколу HTTP в режиме асинхронного задания
 */
public class dataDownloader extends AsyncTask<String, Void, String> {

    private static final String TAG = "DownloadData";

    //@Override
    //protected void onPostExecute(String s) {
    //    super.onPostExecute(s);
    //}


    @Override
    protected String doInBackground(String... strings) {
        String content="";
        try{
            content = download(strings[0]);
        }
        catch (IOException ex){
            Log.e(TAG, "downloadXML: IO Exception reading data: " + ex.getMessage());
        }
        return content;
    }

    private String download(String urlPath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try
        {
            URL url = new URL(urlPath);
            //HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line=null;
            while ((line=reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch(MalformedURLException ex) {
            Log.e(TAG, "download: Invalid URL " + ex.getMessage());
        } catch(IOException ex) {
            Log.e(TAG, "download: IO Exception reading data: " + ex.getMessage());
        } catch(SecurityException ex) {
            Log.e(TAG, "download: Security Exception.  Needs permisson? " + ex.getMessage());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

}
