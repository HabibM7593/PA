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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.benevent.API.NetworkClient;
import com.example.benevent.API.PostApi;
import com.example.benevent.API.UserApi;
import com.example.benevent.Adapter.MyFeedAdapter;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Category;
import com.example.benevent.Models.Post;
import com.example.benevent.Models.User;
import com.example.benevent.R;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        Button addpost = view.findViewById(R.id.button_create_post);
        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePostFragment createPostFragment = new CreatePostFragment();
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_feed, createPostFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView = view.findViewById(R.id.recycler_feed);
        int iduser = pref.getInt("userid", 0);

        PostApi postApi = retrofit.create(PostApi.class);
        Call call = postApi.getPosts(iduser);
        TextView labelEmpty = view.findViewById(R.id.no_post_label);

        final FragmentActivity Post = getActivity();
        LLM = new LinearLayoutManager(Post);
        recyclerView.setLayoutManager(LLM);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                UserApi users = retrofit.create(UserApi.class);
                Call calluser = users.getUsers();
                calluser.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> responseuser) {
                        List<User> listeuser = responseuser.body();
                        List<Post> listepost = response.body();

                        for (int i = 0; i <listepost.size() ; i++) {
                            for (int j = 0; j <listeuser.size() ; j++) {
                                if(listepost.get(i).getIdu()==listeuser.get(j).getId()){
                                    listepost.get(i).setPictureprofiluser(listeuser.get(j).getProfilpicture());
                                    listepost.get(i).setNomprenom(listeuser.get(j).getName()+" "+listeuser.get(j).getFirstname());
                                }
                            }
                        }

                        if(listepost.size() == 0) {
                            labelEmpty.setVisibility(View.VISIBLE);
                        } else {
                            labelEmpty.setVisibility(View.INVISIBLE);
                        }

                        MyFeedAdapter adapter = new MyFeedAdapter(listepost);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });



            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });


        return view;
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
        void onFragmentInteraction(String title);
    }
}