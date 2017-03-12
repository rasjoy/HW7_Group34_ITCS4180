package com.example.joyrasmussen.hw7_group34;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyGridViewHolder> {

    static ArrayList<TED> teds;
    static MainActivity main;

    public GridAdapter(ArrayList<TED> teds, MainActivity main) {
        this.teds = teds;
        this.main = main;
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
        Picasso.with(viewHolder.image.getContext()).cancelRequest(viewHolder.image);
        Picasso.with(viewHolder.image.getContext()).load(imageURL).into(viewHolder.image);

        viewHolder.titleTextView.setText(teds.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return teds.size();
    }

    public static class MyGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView titleTextView;
        ImageButton imageButton;


        public MyGridViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.gridImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.gridTitleTextView);
            imageButton = (ImageButton) itemView.findViewById(R.id.gridImageButton);

            itemView.setOnClickListener(this);
            imageButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {


            if(view.getId() == imageButton.getId()){
                main.onStop();
                int position = getAdapterPosition();
                String mp3URL = teds.get(position).getMp3();
                try {
                    main.playStream(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                int position = getAdapterPosition();
                main.onStop();
                Intent i = new Intent(view.getContext(), PlayActivity.class);
                i.putExtra(MainActivity.TED_PLAY, teds.get(position));
                view.getContext().startActivity(i);

            }

        }
    }
}
