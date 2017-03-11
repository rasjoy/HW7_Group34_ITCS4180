package com.example.joyrasmussen.hw7_group34;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<TED> teds;

    public GridAdapter(ArrayList<TED> teds) {
        this.teds = teds;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.grid_item,
                viewGroup,
                false);

        Adapter.MyViewHolder vh = new Adapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.MyViewHolder viewHolder, int position) {
        
        String imageURL = teds.get(position).getImage();
        Picasso.with(viewHolder.image.getContext()).load(imageURL).into(viewHolder.image);

        viewHolder.titleTextView.setText(teds.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return teds.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;


        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.gridImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.gridTitleTextView);


        }
    }
}
