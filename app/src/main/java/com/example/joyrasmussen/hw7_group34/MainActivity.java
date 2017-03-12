package com.example.joyrasmussen.hw7_group34;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{
    ArrayList<TED> teds;
    final static String URL = "https://www.npr.org/rss/podcast.php?id=510298";

    final static String TED_PLAY = "TED";

    RecyclerView recyclerView;

    String currentLayout;
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    Handler handler;

    LinearLayout linearLayout;
    RelativeLayout mainLayout;
    RecyclerView.MarginLayoutParams params;
    RelativeLayout.LayoutParams loading;
    int currentlyPlaying;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TedAsync(this).execute(URL);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ted_icon);
        actionBar.setDisplayShowHomeEnabled(true);

        handler = new Handler();

        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        params = (RecyclerView.MarginLayoutParams) recyclerView.getLayoutParams();
        linearLayout = (LinearLayout) findViewById(R.id.linLayout);
        mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        currentlyPlaying = -1;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuz, menu);

        return true;
    }



    public void tedArrayList(ArrayList<TED> list){
        this.teds = list;

        linearLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        mainLayout.setBackgroundColor(Color.WHITE);

        //Default layout is list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new Adapter(teds, this);
        recyclerView.setAdapter(adapter);

        currentLayout = "list";

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.switchView) {

            if(currentLayout.equals("list")) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                RecyclerView.Adapter adapter = new GridAdapter(teds, this);
                recyclerView.setAdapter(adapter);
                currentLayout = "grid";
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                RecyclerView.Adapter adapter = new Adapter(teds, this);
                recyclerView.setAdapter(adapter);
                currentLayout = "list";
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playStream(int i) throws IOException {

            if(teds.get(i).getMp3() != null && i != currentlyPlaying) {
                currentlyPlaying = i;
                loading = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
                loading.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                linearLayout.setVisibility(View.VISIBLE);
                changeMargins(300);

                mediaPlayer = new MediaPlayer();

                mediaController = new MediaController(this, false) {
                    @Override
                    public void show(int timeout) {
                        super.show(0);
                    }
                };


                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(teds.get(i).getMp3());
                mediaPlayer.prepareAsync();

                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {

                    }
                });

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer player) {
                        // mediaPlayer.setDisplay(surfaceHolder);
                        mediaPlayer.setScreenOnWhilePlaying(true);
                        mediaController.setMediaPlayer(MainActivity.this);
                        linearLayout.setVisibility(View.INVISIBLE);

                        mediaController.setAnchorView(findViewById(R.id.activity_main));
                        TextView titleText = new TextView(MainActivity.this);
                        titleText.setTextColor(Color.WHITE);
                        titleText.setText(teds.get(currentlyPlaying).getTitle());
                        mediaController.addView(titleText);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mediaController.setEnabled(true);
                                mediaController.show(0);


                            }
                        });
                        player.start();
                    }

                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaController.hide();
                        mediaController = null;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        changeMargins(0);

                        // surfaceView = null;
                        // surfaceHolder = null;


                    }
                });
            }
    }


    @Override
    protected void onStop() {
        super.onStop();
        linearLayout.setVisibility(View.INVISIBLE);
        changeMargins(0);

        if(mediaController!=null){
            mediaController.hide();
            mediaController = null;
        }
        if(mediaPlayer!= null) {

            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;


        }


    }
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        if(mediaController!= null) {
            mediaController.show(0);
        }

        return false;
    }

    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        if(mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                return mediaPlayer.getCurrentPosition();
            }

        }
        return 0;

    }



    public void seekTo(int i) {
if(mediaPlayer != null) {
    mediaPlayer.seekTo(i);
}
    }

    public boolean isPlaying() {
        if(mediaPlayer !=null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer != null){
            onStop();
        }
        super.onDestroy();

}


void changeMargins(int i){
    params.setMargins(0, 0, 0, i);
    recyclerView.setLayoutParams(params);

}
}
