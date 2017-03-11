package com.example.joyrasmussen.hw7_group34;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PlayActivity extends AppCompatActivity {
    TED ted;
    TextView descript, duration, pubDate, title;
    ImageView image;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ted = (TED) getIntent().getSerializableExtra(MainActivity.TED_PLAY);
        descript = (TextView) findViewById(R.id.playDescription);
        title = (TextView) findViewById(R.id.episodeTitle);
        duration = (TextView) findViewById(R.id.playDuration);
        pubDate = (TextView) findViewById(R.id.playPubDate);
        image = (ImageView) findViewById(R.id.imagePlay);
        progressBar = (ProgressBar) findViewById(R.id.playProgress);


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
            Spannable description = new SpannableString(ted.getDescription());
            description.setSpan(new ForegroundColorSpan(Color.BLACK), 0, description.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            descript.append(description);
        }else{
            Spannable description = new SpannableString("N/A"    );
            description.setSpan(new ForegroundColorSpan(Color.BLACK), 0, description.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            descript.append(description);
        }
        if(ted.getDate() != null){
            pubDate.append(ted.getDate());
        }

        if(ted.getDuration() != null){
            duration.append(ted.getDuration());
        }

        if(ted.getTitle() != null){
            title.setText(ted.getTitle());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
