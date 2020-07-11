package com.example.benevent.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benevent.Models.Post;
import com.example.benevent.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyViewHolder> {

    List<Post> listpost;

    public MyPostAdapter(List<Post> listpost) {
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
        public TextView nameAssoPostTV;
        public TextView contentPostTV;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            nameAssoPostTV = v.findViewById(R.id.name_asso_post);
            contentPostTV = v.findViewById(R.id.content_post);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: test");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_post, parent, false);
        MyPostAdapter.MyViewHolder vh = new MyPostAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = listpost.get(position);
        Log.d("TAG", "onBindViewHolder: "+post.getMessage());

        holder.contentPostTV.setText(post.getMessage());
        holder.nameAssoPostTV.setText(post.getAssoname());
    }

    @Override
    public int getItemCount() {
        return listpost.size();
    }

}
