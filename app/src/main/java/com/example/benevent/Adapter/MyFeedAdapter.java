package com.example.benevent.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benevent.Models.Post;
import com.example.benevent.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.MyViewHolder> {

    List<Post> listpost;

    public MyFeedAdapter(List<Post> listpost) {
        this.listpost = listpost;
    }

    public List<Post> getListpost() {
        return listpost;
    }

    public void setListpost(List<Post> listpost) {
        this.listpost = listpost;
        notifyDataSetChanged();
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
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: test");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_feed, parent, false);
        MyFeedAdapter.MyViewHolder vh = new MyFeedAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = listpost.get(position);
        Log.d("TAG", "onBindViewHolder: " + post.getMessage());

        holder.contentPostTV.setText(post.getMessage());
        if (post.getEventname()==null){
            holder.namePostTV.setText(post.getAssoacro());
        }else{
            holder.namePostTV.setText(post.getAssoacro()+" | "+post.getEventname());
        }
    }

    @Override
    public int getItemCount() {
        return listpost.size();
    }

}
