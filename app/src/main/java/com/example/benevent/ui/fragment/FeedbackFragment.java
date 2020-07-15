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
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        int iduserser = pref.getInt("userid", 0);
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText titrefeedback = (EditText) view.findViewById(R.id.object_feedback);
        final EditText contentfeedback = (EditText) view.findViewById(R.id.content_feedback);
        final Switch switchbug = (Switch) view.findViewById(R.id.switch_bug);
        final Button sendfeedback = (Button) view.findViewById(R.id.button_feedback);
        RatingBar ratingbar = (RatingBar) view.findViewById(R.id.rating);
        ratingbar.setStepSize(1);
        switchbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchbug.isChecked()) {
                    ratingbar.setVisibility(View.INVISIBLE);
                    titrefeedback.setVisibility(View.VISIBLE);
                } else {
                    ratingbar.setVisibility(View.VISIBLE);
                    titrefeedback.setVisibility(View.INVISIBLE);
                }
            }
        });

        sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                int type;
                if (switchbug.isChecked()) {
                    type = 1;
                    if (titrefeedback.getText().toString().equals("") || contentfeedback.getText().toString().equals("")) {
                        Toast.makeText(getActivity().getApplicationContext(),"Faites attention à ne pas envoyer de champs vides",Toast.LENGTH_SHORT).show();
                    }else{
                        SendFeedbackBug(new Feedback(iduserser, titrefeedback.getText().toString(), contentfeedback.getText().toString(), formatter.format(date),  type, "ANDROID"));
                        titrefeedback.setText("");
                        contentfeedback.setText("");
                    }
                } else {
                    type = 2;
                    if (contentfeedback.getText().toString().equals("")) {
                        Toast.makeText(getActivity().getApplicationContext(),"Faites attention à ne pas envoyer de champs vides",Toast.LENGTH_SHORT).show();
                    }else{
                        SendFeedbackEval(new Feedback(iduserser, contentfeedback.getText().toString(), formatter.format(date), (int) ratingbar.getRating(), type, "ANDROID"));
                        titrefeedback.setText("");
                        contentfeedback.setText("");
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
                    Toast.makeText(getActivity().getApplicationContext(), "Votre retour a bien ete pris en compte", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez reessayer plus tard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("test", "onFailure: " + t);
                Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Votre retour a bien ete pris en compte", Toast.LENGTH_LONG).show();
                }
                if (response.code() == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez reessayer plus tard", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("test", "onFailure: " + t);
                Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
