package com.example.benevent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.Models.Event;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Event> listEvents;

    public List<Event> getListEvents() {
        return listEvents;
    }

    public void setListEvents(List<Event> listEvents) {
        this.listEvents = listEvents;
        notifyDataSetChanged();
    }

    public MyAdapter(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            view = v;
            textView = v.findViewById(R.id.element_cell_text);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = listEvents.get(position);
        holder.textView.setText(listEvents.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }
}
