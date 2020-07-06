package com.example.benevent.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.API.EventApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Event;
import com.example.benevent.R;
import com.example.benevent.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager LLM;
    List<Event> listevent = new ArrayList<>();
    Retrofit retrofit = NetworkClient.getRetrofitClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView = root.findViewById(R.id.recycler_event);
        EventApi event = retrofit.create(EventApi.class);
        Call call = event.getEvents();

        final FragmentActivity Event = getActivity();
        LLM = new LinearLayoutManager(Event);
        recyclerView.setLayoutManager(LLM);
        call.enqueue(
                new Callback<List<Event>>() {

                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if(response.code()==200) {
                            List<Event> events = response.body();
                            listevent.addAll(events);
                            MyAdapter adapter = new MyAdapter(listevent);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("FailEvent", "onFailure : " + t);
                    }
                }
        );
        return root;

    }
}