package com.example.benevent.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        // Inflate the layout for this fragment
        int iduser = pref.getInt("userid",0);
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText titrefeedback = (EditText) view.findViewById(R.id.object_feedback);
        final EditText contentfeedback = (EditText) view.findViewById(R.id.content_feedback);
        final Switch switchbug = (Switch) view.findViewById(R.id.switch_bug);
        final Button sendfeedback = (Button) view.findViewById(R.id.button_feedback);
        sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type;
                if (switchbug.isChecked()){
                    type=1;
                }else type=2;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                System.out.println(formatter.format(date));
                SendFeedback(new Feedback(iduser,titrefeedback.getText().toString(),contentfeedback.getText().toString(),formatter.format(date),"",type, "ANDROID"));
            }
        });
        return view;
    }


    private void SendFeedback(Feedback feedback){
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        FeedbackApi feedbackApi = retrofit.create(FeedbackApi.class);

        Call call = feedbackApi.sendFeedback(feedback);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("test", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Toast.makeText(getActivity().getApplicationContext(),"Votre retour a bien ete pris en compte", Toast.LENGTH_LONG).show();
                }
                if(response.code()== 500){
                    Toast.makeText(getActivity().getApplicationContext(),"Veuillez reessayer plus tard", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("test", "onFailure: "+t );
                Toast.makeText(getActivity().getApplicationContext(),t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
