
package com.example.benevent.ui.fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.benevent.API.CategoryApi;
import com.example.benevent.API.NetworkClient;
import com.example.benevent.Models.Association;
import com.example.benevent.Models.Category;
import com.example.benevent.R;

import retrofit2.Retrofit;

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

        ImageButton buttonDetailBack = v.findViewById(R.id.back_button_details_asso);
        buttonDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssociationFragment aFragment = new AssociationFragment(categoryAssociation.getName());
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_asso_details, aFragment)
                        .commit();
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
