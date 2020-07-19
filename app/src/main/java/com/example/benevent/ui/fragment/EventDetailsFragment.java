package com.example.benevent.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benevent.API.AssociationApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.ParticipateApi;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Event;
import com.example.benevent.Models.Participation;
import com.example.benevent.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class EventDetailsFragment extends Fragment {

    Event selectedEvent;
    String nameAssociation;
    int iduser;

    public TextView nameEventTV;
    public TextView dateDebEventTV;
    public TextView dateFinEventTV;
    public TextView locationEventTV;
    public TextView descEventTV;
    public TextView maxBEventTV;
    public TextView assoNameTV;
    int valideParticipation = 0;
    int numberParticipation =0 ;

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

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        Button QRcodeButton = view.findViewById(R.id.button_scan_qrcode);
        Button inscriptionButton = view.findViewById(R.id.button_inscription_event);
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        iduser = sharedPreferences.getInt("userid", 0);

        if (selectedEvent.getStartdate().after(new Date())){
            inscriptionButton.setVisibility(View.VISIBLE);
        }
        Participation participation = new Participation(selectedEvent.getIdev(),iduser);
        ParticipateApi participateApi = retrofit.create(ParticipateApi.class);
        Call call = participateApi.checkParticipation(participation.getIdev(),participation.getIdu());
        call.enqueue(new Callback<List<Participation>>(){
            @Override
            public void onResponse(Call<List<Participation>> call, Response<List<Participation>> response) {
                if(response.code()==200){
                    List<Participation> participation =response.body();
                    if (participation.size()==1){
                        inscriptionButton.setText("Se Désinscrire");
                        inscriptionButton.setBackgroundResource(R.drawable.round_corner_red);
                        if (participation.get(0).isStatus()==1 && participation.get(0).getEnddate()==null){
                            valideParticipation=1;
                        }else{
                            valideParticipation=0;
                        }
                    }else{
                        valideParticipation=0;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Participation>> call, Throwable t) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Echec de recuperation du status de participation "+t, Toast.LENGTH_LONG).show();
            }
        });

        Call callcheck = participateApi.checkNumberParticipation(selectedEvent.getIdev());
        callcheck.enqueue(new Callback<List<Participation>>() {
            @Override
            public void onResponse(Call<List<Participation>> call, Response<List<Participation>> response) {
                if (response.code()==200){
                    numberParticipation =  response.body().size();
                    if(selectedEvent.getMaxbenevole()-numberParticipation <1 && valideParticipation==0){
                        inscriptionButton.setVisibility(View.INVISIBLE);
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Cet événement a deja atteint le nombre de participant limite", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Impossible de récuperer le nombre de participant", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Participation>> call, Throwable t) {
            }
        });

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participation participation;
                ParticipateApi participateApi = retrofit.create(ParticipateApi.class);
                if (inscriptionButton.getText().equals("S'inscrire")){
                    participation = new Participation(selectedEvent.getIdev(),iduser,0,1);
                    inscriptionButton.setText("Se Désinscrire");
                    inscriptionButton.setBackgroundResource(R.drawable.round_corner_red);
                    Call call = participateApi.Participer(participation);
                    call.enqueue(new Callback<String>(){
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Vous venez de vous inscrire a cet evenement", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    participation = new Participation(selectedEvent.getIdev(),iduser);
                    inscriptionButton.setText("S'inscrire");
                    inscriptionButton.setBackgroundResource(R.drawable.round_corner_blue);
                    Call call = participateApi.RefuseParticipation(participation.getIdev(),participation.getIdu());
                    call.enqueue(new Callback<String>(){
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Vous venez de vous desinscrire a cet evenement", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        QRcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(EventDetailsFragment.this);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(true);
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
                            nameAssociation = associations.get(0).getName();
                            assoNameTV = view.findViewById(R.id.asso_event_details);
                            assoNameTV.setText(nameAssociation);
                            if (selectedEvent.getStartdate().before(new Date()) && selectedEvent.getEnddate().after(new Date()) && valideParticipation==1){
                                QRcodeButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebEvent = selectedEvent.getStartdate();
        Date dateFinEvent = selectedEvent.getEnddate();

        nameEventTV = view.findViewById(R.id.name_event_details);
        dateDebEventTV = view.findViewById(R.id.dateD_event_details);
        dateFinEventTV = view.findViewById(R.id.dateF_event_details);
        locationEventTV = view.findViewById(R.id.location_event_details);
        descEventTV = view.findViewById(R.id.desc_event_details);
        maxBEventTV = view.findViewById(R.id.maxB_event_details);

        nameEventTV.setText(selectedEvent.getName());
        dateDebEventTV.setText("du : " + formatter.format(dateDebEvent));
        dateFinEventTV.setText("au : " + formatter.format(dateFinEvent));
        locationEventTV.setText(selectedEvent.getLocation());
        descEventTV.setText(selectedEvent.getDescription());
        maxBEventTV.setText(String.valueOf(selectedEvent.getMaxbenevole()));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() != null){

                Log.d("TAG", "onActivityResult: "+intentResult.getContents());
                Participation participation ;


                participation = new Participation(Integer.parseInt(intentResult.getContents()),iduser,1,1);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                participation.setStartdate(formatter.format(date));
                participation.setEnddate(formatter.format(date));
                ParticipateApi participateApi = retrofit.create(ParticipateApi.class);

                Call callAsso = participateApi.UpdateParticipation(participation);
                callAsso.enqueue(
                        new Callback<List<Association>>() {

                            @Override
                            public void onResponse(Call<List<Association>> call, Response<List<Association>> response) {
                                if(response.code()==200) {
                                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Votre scan a bien été pris en compte ", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {

                            }
                        }
                );
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}