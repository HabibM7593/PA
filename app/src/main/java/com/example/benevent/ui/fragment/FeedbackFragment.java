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

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        // Inflate the layout for this fragment
        int iduser = pref.getInt("userid",0);
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final EditText titrefeedback = (EditText) view.findViewById(R.id.edobjetfeedback);
        final EditText contentfeedback = (EditText) view.findViewById(R.id.edcontentfeedback);
        final Switch switchbug = (Switch) view.findViewById(R.id.switchbug);
        final Button sendfeedback = (Button) view.findViewById(R.id.btn_feedback);
        sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type;
                if (switchbug.isChecked()){
                    type=1;
                }else type=2;
                SendFeedback(new Feedback(titrefeedback.getText().toString(),contentfeedback.getText().toString(),type, "ANDROID",iduser));
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
