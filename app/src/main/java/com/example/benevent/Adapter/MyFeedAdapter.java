package com.example.benevent.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benevent.Models.Post;
import com.example.benevent.R;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.MyViewHolder> {

    List<Post> listpost;

    public MyFeedAdapter(List<Post> listpost) {
        this.listpost = listpost;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView namePostTV;
        public TextView contentPostTV;
        public ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            namePostTV = v.findViewById(R.id.name_feed);
            contentPostTV = v.findViewById(R.id.content_feed);
            imageView = v.findViewById(R.id.image_feed);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_feed, parent, false);
        MyFeedAdapter.MyViewHolder vh = new MyFeedAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = listpost.get(position);

        holder.contentPostTV.setText(post.getMessage());

        if (post.getPictureprofiluser() != null) {
            holder.namePostTV.setText(post.getNomprenom() + " | " + post.getEventname());
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(post.getPictureprofiluser());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imageView.setImageBitmap(bmp);
            } catch (IOException | NetworkOnMainThreadException e) {
            }
        } else {
            if (post.getEventname().equals("")) {
                if(post.getAssoacro()==null){
                    holder.namePostTV.setText(post.getAssoname());
                }else{
                    holder.namePostTV.setText(post.getAssoacro());
                }
            } else {
                if(post.getAssoacro()==null){
                    holder.namePostTV.setText(post.getAssoname() + " | " + post.getEventname());
                }else {
                    holder.namePostTV.setText(post.getAssoacro() + " | " + post.getEventname());
                }
            }
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(post.getPictureprofilasso());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imageView.setImageBitmap(bmp);
            } catch (IOException | NetworkOnMainThreadException e) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return listpost.size();
    }

}
