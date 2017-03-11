package com.example.joyrasmussen.hw7_group34;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    public static ArrayList<TED> teds;
    static MainActivity main;

    public Adapter(ArrayList<TED> teds, MainActivity main) {

        this.teds = teds;
        this.main = main;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_item,
                viewGroup,
                false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        String dateText = "Posted: " + teds.get(position).getDate();

        viewHolder.titleTextView.setText(teds.get(position).getTitle());
        viewHolder.dateTextView.setText(dateText);

        String imageURL = teds.get(position).getImage();

        Picasso.with(viewHolder.image.getContext()).load(imageURL).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        if (teds != null) {
            return teds.size();
        } else {
            return -1;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView dateTextView;
        public TextView titleTextView;
        public ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.gridImageView);
            dateTextView = (TextView) itemView.findViewById(R.id.listDateTitleTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.gridTitleTextView);
            imageButton = (ImageButton) itemView.findViewById(R.id.listImageButton);

            itemView.setOnClickListener(this);
            imageButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {


            if(view.getId() == imageButton.getId()){
                int position = getAdapterPosition();
                String mp3URL = teds.get(position).getMp3();
                try {
                    main.playStream(mp3URL);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } else {
                int position = getAdapterPosition();
                //Log.d("Onclick", teds.get(position).getTitle());
                main.onStop();
                Intent i = new Intent(view.getContext(), PlayActivity.class);
                i.putExtra(MainActivity.TED_PLAY, teds.get(position));
                view.getContext().startActivity(i);

            }

        }
    }
}
