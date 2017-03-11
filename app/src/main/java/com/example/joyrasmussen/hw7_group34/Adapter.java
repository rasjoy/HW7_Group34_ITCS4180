package com.example.joyrasmussen.hw7_group34;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<TED> teds;

    public Adapter(ArrayList<TED> teds) {
        this.teds = teds;
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView dateTextView;
        public TextView titleTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.listImageView);
            dateTextView = (TextView) itemView.findViewById(R.id.listDateTitleTextView);
            titleTextView = (TextView) itemView.findViewById(R.id.listTitleTextView);

        }
    }
}
