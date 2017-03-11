package com.example.joyrasmussen.hw7_group34;

import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joyrasmussen on 3/11/17.
 */

public class TED {

    String title, description, image, duration, mp3;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String datez) throws ParseException {
        //Fri, 10 Mar 2017 00:01:17 -0500
        DateFormat sdfFrom =   new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
                //new SimpleDateFormat("EEE, DD MMM yyy hh:mm:ss 'Z'");
        DateFormat sdf = new SimpleDateFormat("EEE, dd, MMM yyyy");


        this.date = sdf.format(sdfFrom.parse(datez));
    }

    public String toString(){
        return title;

    }
}
