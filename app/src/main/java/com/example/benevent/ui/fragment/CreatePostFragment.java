package com.example.benevent.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.benevent.API.EventApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.PostApi;
import com.example.benevent.Models.Event;
import com.example.benevent.Models.Post;
import com.example.benevent.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class CreatePostFragment extends Fragment {

    Retrofit retrofit = NetworkClient.getRetrofitClient();
    Event selectedEvent = new Event();

    public CreatePostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        EditText contentET = view.findViewById(R.id.content_post_edit_text);
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        int iduser = sharedPreferences.getInt("userid", 0);
        List<Event> listEvent = new ArrayList<>();

        Spinner eventListSpinner = view.findViewById(R.id.evenement_spinner);
        Button submitButton = view.findViewById(R.id.button_post);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post();
                post.setMessage(contentET.getText().toString());
                post.setIdu(iduser);
                post.setIdev(selectedEvent.getIdev());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                post.setDate(formatter.format(date));

                if (post.getIdev() == 0) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Vous ne pouvez pas publier sans selectionner un evenement !", Toast.LENGTH_LONG).show();
                } else {
                    PostApi postApi = retrofit.create(PostApi.class);
                    Call call = postApi.sendPost(post);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200) {
                                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Votre post a bien ete pris en compte !", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Impossible d'envoyer ce post", Toast.LENGTH_LONG).show();
                        }
                    });


                    FeedFragment feedFragment = new FeedFragment();
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_create_post, feedFragment)
                            .commit();
                }
            }
        });

        EventApi eventApi = retrofit.create(EventApi.class);
        Call call = eventApi.getEvents(iduser);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getFakeevent() == 0) {
                            listEvent.add(response.body().get(i));
                        }
                    }
                    ArrayAdapter<Event> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(), android.R.layout.simple_spinner_item, listEvent);
                    eventListSpinner.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "impossible de recuperer le contenue des events ", Toast.LENGTH_LONG).show();
            }
        });

        eventListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEvent = (Event) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }
}