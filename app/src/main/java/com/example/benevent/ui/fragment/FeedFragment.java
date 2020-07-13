package com.example.benevent.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.PostApi;
import com.example.benevent.Adapter.MyFeedAdapter;
import com.example.benevent.Models.Post;
import com.example.benevent.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FeedFragment extends Fragment {

    private FeedFragment.OnFragmentInteractionListener mListener;
    Retrofit retrofit = NetworkClient.getRetrofitClient();
    private RecyclerView recyclerView;
    private LinearLayoutManager LLM;

    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        // Inflate the layout for this fragment
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recycler_feed);
        int iduser = pref.getInt("userid", 0);

        PostApi event = retrofit.create(PostApi.class);
        Call call = event.getPosts(iduser);

        final FragmentActivity Post = getActivity();
        LLM = new LinearLayoutManager(Post);
        recyclerView.setLayoutManager(LLM);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> listepost = response.body();
                MyFeedAdapter adapter = new MyFeedAdapter(listepost);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


        return view ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedFragment.OnFragmentInteractionListener) {
            mListener = (FeedFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);    }
}