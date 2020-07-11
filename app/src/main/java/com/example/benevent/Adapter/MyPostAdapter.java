package com.example.benevent.Adapter;

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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
}
