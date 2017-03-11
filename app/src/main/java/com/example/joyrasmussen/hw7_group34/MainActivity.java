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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TED> teds;
    final static String URL = "https://www.npr.org/rss/podcast.php?id=510298";
    RecyclerView recyclerView;

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
        //Log.d( "tedArrayList: ", teds.toString());

        //Default layout is list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new Adapter(teds);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.switchView) {

            Log.i("sfsdf", "'sdgsdf");

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            RecyclerView.Adapter adapter = new GridAdapter(teds);
            recyclerView.setAdapter(adapter);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
