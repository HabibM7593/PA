

package com.example.benevent.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.Models.Association;
import com.example.benevent.Models.Category;
import com.example.benevent.R;
import com.example.benevent.ui.fragment.AssociationDetailsFragment;

import java.util.List;

public class MyAssoAdapter extends RecyclerView.Adapter<MyAssoAdapter.MyViewHolder> {

    private List<Association> listAssos;
    private List<Category> listCategory;

    public List<Association> getListAssos() {
        return listAssos;
    }

    public List<Category> getListCategory() { return listCategory; }

    public void setListAssos(List<Association> listAssos) {
        this.listAssos = listAssos;
        notifyDataSetChanged();
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public MyAssoAdapter(List<Association> listAssos, List<Category> listCategory) {
        this.listAssos = listAssos;
        this.listCategory = listCategory;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView nameAssoTV;
        public TextView catAssoTV;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            nameAssoTV = v.findViewById(R.id.name_asso);
            catAssoTV = v.findViewById(R.id.category_asso);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_asso, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        ImageButton buttonDetailShow = v.findViewById(R.id.button_asso_view);
        buttonDetailShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Association newAssociation = listAssos.get(vh.getAdapterPosition());
                Category newCategory = listCategory.get(vh.getAdapterPosition());

                AssociationDetailsFragment adFragment = new AssociationDetailsFragment(newAssociation, newCategory);
                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_asso, adFragment)
                        .commit();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Association association = listAssos.get(position);
        Category category = listCategory.get(association.getIdcat());

        holder.nameAssoTV.setText(association.getName());
        holder.catAssoTV.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return listAssos.size();
    }
}

