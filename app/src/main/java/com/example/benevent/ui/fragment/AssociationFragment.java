package com.example.benevent.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benevent.API.AssociationApi;
import com.example.benevent.API.CategoryApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Adapter.MyAssoAdapter;
import com.example.benevent.Models.Association;
import com.example.benevent.Adapter.MyEventAdapter;
import com.example.benevent.Models.Category;
import com.example.benevent.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AssociationFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager LLM;
    String filter = "";
    List<Association> listAsso = new ArrayList<>();
    List<Category> listCat = new ArrayList<>();
    Retrofit retrofit = NetworkClient.getRetrofitClient();

    public AssociationFragment(String filter) {
        this.filter = filter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_association, container, false);
        recyclerView = root.findViewById(R.id.recycler_asso);

        AssociationApi assoApi = retrofit.create(AssociationApi.class);
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);

        Call callAsso = assoApi.getAllAssos();

        final FragmentActivity Association = getActivity();
        LLM = new LinearLayoutManager(Association);
        recyclerView.setLayoutManager(LLM);
        callAsso.enqueue(
                new Callback<List<Association>>() {

                    @Override
                    public void onResponse(Call<List<Association>> call, Response<List<Association>> responseAsso) {
                        if(responseAsso.code()==200) {
                            List<Association> associations = responseAsso.body();
                            listAsso.addAll(associations);
                            Call callCat = categoryApi.getCat();

                            callCat.enqueue(
                                    new Callback<List<Category>>() {

                                        @Override
                                        public void onResponse(Call<List<Category>> call, Response<List<Category>> responseCat) {
                                            if(responseCat.code()==200) {
                                                if(filter != ""){
                                                }

                                                List<Category> category = responseCat.body();
                                                listCat.addAll(category);
                                                MyAssoAdapter adapter = new MyAssoAdapter(listAsso,listCat);
                                                recyclerView.setAdapter(adapter);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                            Log.d("FailCategory", "onFailure : " + t);
                                        }
                                    }
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.d("FailAsso", "onFailure : " + t);
                    }
                }
        );
        return root;
    }
}