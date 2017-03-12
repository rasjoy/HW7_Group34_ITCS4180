package com.example.joyrasmussen.hw7_group34;

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
import android.widget.MediaController;
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


    public void playStream(String url) throws IOException {
            mediaController = new MediaController(this);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaController = new MediaController(this);
            mediaController.show();
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {

                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer player) {
                    mediaController.setMediaPlayer(MainActivity.this);
                    mediaController.setAnchorView(findViewById(R.id.activity_main));
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            mediaController.setEnabled(true);
                            mediaController.show();

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
                    mediaPlayer=null;


                }
            });

            mediaPlayer.setScreenOnWhilePlaying(true);


    }


    @Override
    protected void onStop() {
        super.onStop();
        if(mediaController!=null){
            mediaController.hide();
        }
        if(mediaPlayer!= null) {
            //    Log.d("Stopping", "Stopping");
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
            mediaController.show();
        }

        return false;
    }


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

    /* public int getCurrentPosition() {

             return 0;

     }
 */
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        if(mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }else{
            return false;
        }
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
}
