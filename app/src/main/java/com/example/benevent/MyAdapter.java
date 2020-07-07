package com.example.benevent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.Models.Event;
import com.example.benevent.ui.fragment.EventDetailsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        public TextView nameEventTV;
        public TextView dateEventTV;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            nameEventTV = v.findViewById(R.id.name_event);
            dateEventTV = v.findViewById(R.id.date_event);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        ImageButton bt1 = v.findViewById(R.id.button_event_view);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDetailsFragment edFragment = new EventDetailsFragment();
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_event, edFragment)
                        .commit();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = listEvents.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateEvent = event.getDateDeb();
        Date dateToday = new Date();

        holder.nameEventTV.setText(event.getName());

        if (dateEvent.after(dateToday)) {
            holder.dateEventTV.setText("Debutera le " + formatter.format(dateEvent));
            holder.dateEventTV.setTextColor(0xFF82C26E);
        } else if (dateEvent.equals(dateToday)) {
            holder.dateEventTV.setText("Débuté depuis le " + formatter.format(dateEvent));
            holder.dateEventTV.setTextColor(0XFF858181);
        } else if (dateEvent.before(dateToday)) {
            holder.dateEventTV.setText("Terminé le " + formatter.format(dateEvent));
            holder.dateEventTV.setTextColor(0XFFEB1C1C);
        }
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }
}
