package com.example.benevent.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.benevent.API.AssociationApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Event;
import com.example.benevent.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventDetailsFragment extends Fragment {

    Event selectedEvent;
    String nameAsso;

    public TextView nameEventTV;
    public TextView dateDebEventTV;
    public TextView dateFinEventTV;
    public TextView locationEventTV;
    public TextView descEventTV;
    public TextView maxBEventTV;
    public TextView assoTV;

    Retrofit retrofit = NetworkClient.getRetrofitClient();

    public EventDetailsFragment(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_event_details, container, false);

        ImageButton buttonDetailBack = v.findViewById(R.id.back_button_details_event);
        buttonDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventFragment eFragment = new EventFragment();
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_event_detail, eFragment)
                        .commit();
            }
        });

        AssociationApi association = retrofit.create(AssociationApi.class);
        Call callAsso = association.getAsso(selectedEvent.getIdas());

        callAsso.enqueue(
                new Callback<List<Association>>() {

                    @Override
                    public void onResponse(Call<List<Association>> call, Response<List<Association>> response) {
                        if(response.code()==200) {
                            List<Association> associations = response.body();
                            nameAsso = associations.get(0).getName();
                            assoTV = v.findViewById(R.id.asso_event_details);
                            assoTV.setText(nameAsso);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("FailEvent", "onFailure : " + t);
                    }
                }
        );

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebEvent = selectedEvent.getDateDeb();
        Date dateFinEvent = selectedEvent.getDateFin();

        nameEventTV = v.findViewById(R.id.name_event_details);
        dateDebEventTV = v.findViewById(R.id.dateD_event_details);
        dateFinEventTV = v.findViewById(R.id.dateF_event_details);
        locationEventTV = v.findViewById(R.id.location_event_details);
        descEventTV = v.findViewById(R.id.desc_event_details);
        maxBEventTV = v.findViewById(R.id.maxB_event_details);


        nameEventTV.setText(selectedEvent.getName());
        dateDebEventTV.setText("du : " + formatter.format(dateDebEvent));
        dateFinEventTV.setText("au : " + formatter.format(dateFinEvent));
        locationEventTV.setText(selectedEvent.getLocation());
        descEventTV.setText(selectedEvent.getDescription());
        maxBEventTV.setText(String.valueOf(selectedEvent.getMaxbenevole()));


        return v;
    }
}