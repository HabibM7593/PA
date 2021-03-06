package com.example.benevent.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.API.CategoryApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Adapter.MyCategoryAdapter;
import com.example.benevent.Models.Category;
import com.example.benevent.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    List<Category> listCategory = new ArrayList<>();
    Retrofit retrofit = NetworkClient.getRetrofitClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.recycler_category);

        final FragmentActivity Event = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Event);
        recyclerView.setLayoutManager(linearLayoutManager);
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);
        Call callCat = categoryApi.getCat();
        callCat.enqueue(
                new Callback<List<Category>>() {

                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.code() == 200) {
                            List<Category> category = response.body();
                            listCategory.addAll(category);
                            MyCategoryAdapter adapter = new MyCategoryAdapter(listCategory);
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