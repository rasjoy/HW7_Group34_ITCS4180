package com.example.joyrasmussen.hw7_group34;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{
    TED ted;
    TextView descript, duration, pubDate, title;
    ImageView image;
    ProgressBar progressBar;
    RelativeLayout activityLayout;
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    Handler handler;
    LinearLayout loading;
    ScrollView scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ted = (TED) getIntent().getSerializableExtra(MainActivity.TED_PLAY);
       // Log.d("play", ted.getTitle());
        descript = (TextView) findViewById(R.id.playDescription);
        title = (TextView) findViewById(R.id.episodeTitle);
        duration = (TextView) findViewById(R.id.playDuration);
        pubDate = (TextView) findViewById(R.id.playPubDate);
        image = (ImageView) findViewById(R.id.imagePlay);
        progressBar = (ProgressBar) findViewById(R.id.playProgress);
         activityLayout = (RelativeLayout) findViewById(R.id.activity_play);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        loading = (LinearLayout) findViewById(R.id.loadingEpisode);
        handler = new Handler();
        setTed();
        if(ted.getMp3() != null){
        try {
            playStream(ted.getMp3());
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    }

    public void setTed() {
        if (ted.getImage() != null) {
            Picasso.with(this).load(ted.getImage()).into(image, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError() {

                }
            });
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
        if(ted.getDescription() != null) {
            descript.append(" " + ted.getDescription());

        }else{
            descript.append("  N/A");
        }
        if(ted.getDate() != null){
            pubDate.append(" " +ted.getDate());
        }

        if(ted.getDuration() != null){
            int dur = Integer.parseInt(ted.getDuration());

            duration.append(" "+ dur/60 +":"+ (dur%60) );
        }else{duration.append(" N/A");}


        if(ted.getTitle() != null){
            title.setText(" " +ted.getTitle());
        }

    }

    public void playStream(String url) throws IOException {



        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(this, false){
            @Override
            public void show(int timeout) {
                super.show(0);
            }};


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepareAsync();

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer player) {

                mediaPlayer.setScreenOnWhilePlaying(true);
                mediaController.setMediaPlayer(PlayActivity.this);
                mediaController.setAnchorView(findViewById(R.id.activity_play));
                loading.setVisibility(View.GONE);
                handler.post(new Runnable(){
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


            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

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

                return mediaPlayer.getCurrentPosition();


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
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
        }
        finish();
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (action == KeyEvent.ACTION_UP || action == KeyEvent.ACTION_DOWN) {
                    // TODO


                    finish();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }

    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

}
