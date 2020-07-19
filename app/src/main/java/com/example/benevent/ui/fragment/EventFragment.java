package com.example.benevent.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.API.EventApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Event;
import com.example.benevent.R;
import com.example.benevent.Adapter.MyEventAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class EventFragment extends Fragment {

    private RecyclerView recyclerView;
    List<Event> listevent = new ArrayList<>();
    Retrofit retrofit = NetworkClient.getRetrofitClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        recyclerView = view.findViewById(R.id.recycler_event);
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("login", MODE_PRIVATE);
        int iduser = sharedPreferences.getInt("userid", 0);

        TextView labelEmpty = view.findViewById(R.id.no_event_label);

        final FragmentActivity Event = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Event);
        recyclerView.setLayoutManager(linearLayoutManager);

        EventApi eventApi = retrofit.create(EventApi.class);
        Call callEvent = eventApi.getEvents(iduser);
        callEvent.enqueue(
                new Callback<List<Event>>() {

                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if(response.code()==200) {
                            List<Event> events = response.body();
                            for(int i=0;i<events.size();i++){
                                if (events.get(i).getFakeevent()==0){
                                    listevent.add(events.get(i));
                                }
                            }

                            if(events.size() == 0) {
                                labelEmpty.setVisibility(View.VISIBLE);
                            } else {
                                labelEmpty.setVisibility(View.INVISIBLE);
                            }

                            MyEventAdapter adapter = new MyEventAdapter(listevent);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                    }
                }
        );
        return view;

    }
}