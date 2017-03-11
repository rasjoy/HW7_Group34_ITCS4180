package com.example.joyrasmussen.hw7_group34;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joyrasmussen on 3/11/17.
 */

public class TED {

    String title, description, image, duration, mp3;
    Date date;

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

    public Date getDate() {

        return date;
    }

    public void setDate(String date) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("EEE, dd, MMM yyyy");
        this.date = sdf.parse(date);
    }

    public String toString(){
        return title;

    }
}
