package com.example.joyrasmussen.hw7_group34;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<TED> teds;
    final static String URL = "https://www.npr.org/rss/podcast.php?id=510298";
    RecyclerView recyclerView;

    String currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TedAsync(this).execute(URL);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ted_icon);
        actionBar.setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuz, menu);
        return true;
    }



    public void tedArrayList(ArrayList<TED> list){
        this.teds = list;
        sort();

        //Default layout is list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new Adapter(teds);
        recyclerView.setAdapter(adapter);

        currentLayout = "list";

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.switchView) {

            if(currentLayout.equals("list")) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                RecyclerView.Adapter adapter = new GridAdapter(teds);
                recyclerView.setAdapter(adapter);
                currentLayout = "grid";
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                RecyclerView.Adapter adapter = new Adapter(teds);
                recyclerView.setAdapter(adapter);
                currentLayout = "list";
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sort(){

        Collections.sort(teds, new Comparator<TED>() {
            @Override
            public int compare(TED t1, TED t2) {

                DateFormat df = new SimpleDateFormat("EEE, dd, MMM yyyy");

                try {
                    Date date1 = df.parse(t1.date);
                    Date date2 = df.parse(t2.date);

                    return date2.compareTo(date1); //most recent dates first

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return 0;
            }


        });

    }
}
