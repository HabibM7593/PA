package com.example.benevent.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.benevent.API.FeedbackApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Feedback;
import com.example.benevent.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class FeedbackFragment extends Fragment {

    public FeedbackFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);

        int iduser = sharedPreferences.getInt("userid", 0);
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText titrefeedbackET = (EditText) view.findViewById(R.id.object_feedback);
        final EditText contentfeedbackET = (EditText) view.findViewById(R.id.content_feedback);
        RatingBar ratingbar = (RatingBar) view.findViewById(R.id.rating);
        ratingbar.setStepSize(1);
        final Switch bugSwitch = (Switch) view.findViewById(R.id.switch_bug);
        bugSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bugSwitch.isChecked()) {
                    ratingbar.setVisibility(View.INVISIBLE);
                    titrefeedbackET.setVisibility(View.VISIBLE);
                } else {
                    ratingbar.setVisibility(View.VISIBLE);
                    titrefeedbackET.setVisibility(View.INVISIBLE);
                }
            }
        });

        final Button sendfeedbackButton = (Button) view.findViewById(R.id.button_feedback);
        sendfeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                int type;
                if (bugSwitch.isChecked()) {
                    type = 1;
                    if (titrefeedbackET.getText().toString().equals("") || contentfeedbackET.getText().toString().equals("")) {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"Faites attention à ne pas envoyer de champs vides",Toast.LENGTH_SHORT).show();
                    }else{
                        SendFeedbackBug(new Feedback(iduser, titrefeedbackET.getText().toString(), contentfeedbackET.getText().toString(), formatter.format(date),  type, "ANDROID"));
                        titrefeedbackET.setText("");
                        contentfeedbackET.setText("");
                    }
                } else {
                    type = 2;
                    if (contentfeedbackET.getText().toString().equals("")) {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"Faites attention à ne pas envoyer de champs vides",Toast.LENGTH_SHORT).show();
                    }else{
                        SendFeedbackEval(new Feedback(iduser, contentfeedbackET.getText().toString(), formatter.format(date), (int) ratingbar.getRating(), type, "ANDROID"));
                        titrefeedbackET.setText("");
                        contentfeedbackET.setText("");
                    }
                }
            }
        });
        return view;
    }


    private void SendFeedbackBug(Feedback feedback) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        FeedbackApi feedbackApi = retrofit.create(FeedbackApi.class);

        Call call = feedbackApi.sendFeedbackBug(feedback);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Votre retour a bien ete pris en compte", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Veuillez reessayer plus tard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("test", "onFailure: " + t);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void SendFeedbackEval(Feedback feedback) {
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        FeedbackApi feedbackApi = retrofit.create(FeedbackApi.class);

        Call call = feedbackApi.sendFeedbackEval(feedback);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Votre retour a bien ete pris en compte", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Veuillez reessayer plus tard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("test", "onFailure: " + t);
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
