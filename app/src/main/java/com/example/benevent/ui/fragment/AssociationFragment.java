package com.example.benevent.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benevent.API.AssociationApi;
import com.example.benevent.API.CategoryApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Adapter.MyAssoAdapter;
import com.example.benevent.Models.Association;

import com.example.benevent.Models.Category;
import com.example.benevent.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AssociationFragment extends Fragment {

    private RecyclerView recyclerView;
    String filter = "";
    List<Association> listAssociation = new ArrayList<>();
    List<Category> listCategories = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_association, container, false);
        recyclerView = view.findViewById(R.id.recycler_asso);
        TextView labelEmpty = view.findViewById(R.id.no_asso_label);

        final FragmentActivity Association = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Association);
        recyclerView.setLayoutManager(linearLayoutManager);
        listAssociation.clear();
        AssociationApi associationApi = retrofit.create(AssociationApi.class);
        Call callAsso = associationApi.getAllAssos();
        callAsso.enqueue(
                new Callback<List<Association>>() {
                    @Override
                    public void onResponse(Call<List<Association>> call, Response<List<Association>> responseAsso) {
                        if (responseAsso.code() == 200) {
                            CategoryApi categoryApi = retrofit.create(CategoryApi.class);
                            Call callCat = categoryApi.getCat();
                            callCat.enqueue(
                                    new Callback<List<Category>>() {
                                        @Override
                                        public void onResponse(Call<List<Category>> call, Response<List<Category>> responseCat) {
                                            if (responseCat.code() == 200) {
                                                List<Category> currentCategory = responseCat.body();
                                                Predicate<Category> filtercategory = category -> category.getName().equals(filter);
                                                List<Category> result = currentCategory.stream()
                                                        .filter(filtercategory)
                                                        .collect(Collectors.toList());
                                                List<Association> associations = responseAsso.body();
                                                Predicate<Association> byIdcat = association -> association.getIdcat() == result.get(0).getIdcat();
                                                List<Association> filteredAssociation = associations.stream()
                                                        .filter(byIdcat)
                                                        .collect(Collectors.toList());
                                                listAssociation.addAll(filteredAssociation);
                                                listCategories.addAll(currentCategory);

                                                if (listAssociation.size() == 0) {
                                                    labelEmpty.setVisibility(View.VISIBLE);
                                                } else {
                                                    labelEmpty.setVisibility(View.INVISIBLE);
                                                }

                                                MyAssoAdapter myAssoAdapter = new MyAssoAdapter(listAssociation, result.get(0));
                                                recyclerView.setAdapter(myAssoAdapter);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call call, Throwable t) {
                                        }
                                    }
                            );
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