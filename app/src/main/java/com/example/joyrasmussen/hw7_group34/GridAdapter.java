package com.example.joyrasmussen.hw7_group34;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyGridViewHolder> {

    ArrayList<TED> teds;

    public GridAdapter(ArrayList<TED> teds) {
        this.teds = teds;
    }

    @Override
    public GridAdapter.MyGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.grid_item,
                viewGroup,
                false);

        GridAdapter.MyGridViewHolder vh = new GridAdapter.MyGridViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GridAdapter.MyGridViewHolder viewHolder, int position) {

        String imageURL = teds.get(position).getImage();
        Picasso.with(viewHolder.image.getContext()).load(imageURL).into(viewHolder.image);

        viewHolder.titleTextView.setText(teds.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return teds.size();
    }

    public static class MyGridViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;


        public MyGridViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.gridImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.gridTitleTextView);


        }
    }
}
