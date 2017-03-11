package com.example.joyrasmussen.hw7_group34;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by joyrasmussen on 3/11/17.
 */

public class TedAsync extends AsyncTask<String, Void, ArrayList<TED>> {

    MainActivity mainActivity;
    public TedAsync(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    protected ArrayList<TED> doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                return TedUtil.TedPullParser.tedParser(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TED> teds) {
       mainActivity.tedArrayList(teds);
        super.onPostExecute(teds);

    }
}
