
package com.example.benevent.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benevent.API.FollowApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Category;
import com.example.benevent.Models.Follow;
import com.example.benevent.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class AssociationDetailsFragment extends Fragment {

    Association selectedAssociation;
    Category categoryAssociation;

    String nameCategory;

    public TextView nameAssoTV;
    public TextView nameCategoryTV;
    public TextView emailTV;
    public TextView phoneTV;
    public TextView websiteTV;
    public TextView supportTV;

    Retrofit retrofit = NetworkClient.getRetrofitClient();

    public AssociationDetailsFragment(Association selectedAssociation, Category categoryAssociation) {
        this.selectedAssociation = selectedAssociation;
        this.categoryAssociation = categoryAssociation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_association_details, container, false);
        Button followAsso = v.findViewById(R.id.button_follow);
        FollowApi followApi = retrofit.create(FollowApi.class);
        SharedPreferences pref = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        int iduser = pref.getInt("userid", 0);
        Follow follow = new Follow(selectedAssociation.getIdas(),iduser);
        ImageButton buttonDetailBack = v.findViewById(R.id.back_button_details_asso);
        Call call = followApi.checkFollow(follow.getIdas(),follow.getIdu());
        call.enqueue(new Callback<List<Follow>>(){

            @Override
            public void onResponse(Call<List<Follow>> call, Response<List<Follow>> response) {
                if(response.code()==200){
                    List<Follow> follows =response.body();
                    if (follows.size()==1){
                        followAsso.setText("Ne plus suivre");
                        followAsso.setBackgroundColor(0xFFFF0000);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Follow>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
            }
        });
        buttonDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+nameCategoryTV.getText());
                AssociationFragment aFragment = new AssociationFragment(categoryAssociation.getName());
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_asso_details, aFragment)
                        .commit();
            }
        });
        followAsso.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (followAsso.getText().equals("Suivre")){
                    followAsso.setText("Ne plus suivre");
                    followAsso.setBackgroundColor(0xFFFF0000);
                    Call call = followApi.followAsso(follow);
                    call.enqueue(new Callback<String>(){

                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Vous suivez cette association", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();

                        }
                    });
                }else{
                    followAsso.setText("Suivre");
                    followAsso.setBackgroundColor(R.drawable.round_corner_blue);
                    Call call = followApi.unfollowAsso(follow);
                    call.enqueue(new Callback<String>(){
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getActivity().getApplicationContext(), "Vous ne suivez plus cette association", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), "Echec", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        nameAssoTV = v.findViewById(R.id.name_asso_details);
        nameCategoryTV = v.findViewById(R.id.name_category_details);
        emailTV = v.findViewById(R.id.email_asso_details);
        phoneTV = v.findViewById(R.id.phone_asso_details);
        websiteTV = v.findViewById(R.id.website_asso_details);
        supportTV = v.findViewById(R.id.support_asso_details);

        nameAssoTV.setText(selectedAssociation.getName() + " / " + selectedAssociation.getAcronym());
        nameCategoryTV.setText(categoryAssociation.getName());
        emailTV.setText(selectedAssociation.getEmail());
        phoneTV.setText(selectedAssociation.getPhone());
        websiteTV.setText(selectedAssociation.getWebsite());
        supportTV.setText(selectedAssociation.getSupport());

        return v;
    }
}
