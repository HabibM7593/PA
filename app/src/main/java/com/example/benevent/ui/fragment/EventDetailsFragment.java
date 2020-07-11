package com.example.benevent.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benevent.API.AssociationApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.ParticipateApi;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Event;
import com.example.benevent.Models.Follow;
import com.example.benevent.Models.Participation;
import com.example.benevent.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

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
        Button buttonQRcode = v.findViewById(R.id.button_scan_qrcode);
        Button buttoninscription = v.findViewById(R.id.button_inscription_event);
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        int iduser = pref.getInt("userid", 0);

        if (selectedEvent.getDateDeb().after(new Date())){
            buttoninscription.setVisibility(View.VISIBLE);
        }
        Participation participation = new Participation(selectedEvent.getIdev(),iduser);
        ParticipateApi participateApi = retrofit.create(ParticipateApi.class);
        Call call = participateApi.checkFollow(participation.getIdev(),participation.getIdu());
        call.enqueue(new Callback<List<Follow>>(){

            @Override
            public void onResponse(Call<List<Follow>> call, Response<List<Follow>> response) {
                if(response.code()==200){
                    List<Follow> follows =response.body();
                    if (follows.size()==1){
                        buttoninscription.setText("Se Désinscrire");
                        buttoninscription.setBackgroundColor(0xFFFF0000);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Follow>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
            }
        });
        buttonDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventFragment eFragment = new EventFragment();
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_event_detail, eFragment)
                        .commit();
            }
        });

        buttoninscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participation participation = new Participation(selectedEvent.getIdev(),iduser,false,true);
                ParticipateApi participateApi = retrofit.create(ParticipateApi.class);
                if (buttoninscription.getText().equals("Inscription")){
                    buttoninscription.setText("Se Désinscrire");
                    buttoninscription.setBackgroundColor(0xFFFF0000);
                    Call call = participateApi.UpdateParticipation(participation);
                    call.enqueue(new Callback<String>(){

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Vous suivez cette association", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                    buttoninscription.setText("Inscription");
                    buttoninscription.setBackgroundColor(R.drawable.round_corner);
                    Call call = participateApi.RefuseParticipation(participation);
                    call.enqueue(new Callback<String>(){
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Vous ne suivez plus cette association", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        buttonQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(EventDetailsFragment.this);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(true);
                //The following line if you want QR code
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setOrientationLocked(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
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
                            if (selectedEvent.getDateDeb().before(new Date()) && selectedEvent.getDateFin().after(new Date())){
                                buttonQRcode.setVisibility(View.VISIBLE);
                            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
            }else {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}