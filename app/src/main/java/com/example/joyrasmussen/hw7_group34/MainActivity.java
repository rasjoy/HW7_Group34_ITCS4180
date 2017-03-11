package com.example.joyrasmussen.hw7_group34;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TED> teds;
    final static String URL = "https://www.npr.org/rss/podcast.php?id=510298";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TedAsync(this).execute(URL);


    }



    public void tedArrayList(ArrayList<TED> list){
        teds = list;
        Log.d( "tedArrayList: ", teds.toString());
    }


}
