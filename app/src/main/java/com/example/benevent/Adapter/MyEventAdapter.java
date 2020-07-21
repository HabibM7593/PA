package com.example.benevent.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.Models.Event;
import com.example.benevent.R;
import com.example.benevent.ui.fragment.EventDetailsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.MyViewHolder> {

    private List<Event> listEvents;

    public MyEventAdapter(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView nameEventTV;
        public TextView dateEventTV;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            nameEventTV = view.findViewById(R.id.name_event);
            dateEventTV = view.findViewById(R.id.date_event);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_event, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        ImageButton detailShowButton = view.findViewById(R.id.button_event_view);
        detailShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event newEvent = listEvents.get(myViewHolder.getAdapterPosition());

                EventDetailsFragment eventDetailsFragment = new EventDetailsFragment(newEvent);
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_event, eventDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event event = listEvents.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebutEvent = event.getStartdate();
        Date dateFinEvent = event.getEnddate();
        Date dateToday = new Date();

        holder.nameEventTV.setText(event.getName());

        if (dateDebutEvent.after(dateToday)) {
            holder.dateEventTV.setText("Debutera le " + formatter.format(dateDebutEvent));
            holder.dateEventTV.setTextColor(0xFF82C26E);
        } else if (dateDebutEvent.before(dateToday) && dateFinEvent.after(dateToday)) {
            holder.dateEventTV.setText("Débuté depuis le " + formatter.format(dateDebutEvent));
            holder.dateEventTV.setTextColor(0XFF858181);
        } else if (dateFinEvent.before(dateToday)) {
            holder.dateEventTV.setText("Terminé le " + formatter.format(dateDebutEvent));
            holder.dateEventTV.setTextColor(0XFFEB1C1C);
        }
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }
}
