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
    private Category category;

    public MyAssoAdapter(List<Association> listAssos, Category category) {
        this.listAssos = listAssos;
        this.category = category;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView nameAssoTV;
        public TextView catAssoTV;
        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            nameAssoTV = view.findViewById(R.id.name_asso);
            catAssoTV = view.findViewById(R.id.category_asso);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_asso, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        ImageButton buttonDetailShow = view.findViewById(R.id.button_asso_view);
        buttonDetailShow.setOnClickListener(loadingView -> {
            Association newAssociation = listAssos.get(viewHolder.getAdapterPosition());

            AssociationDetailsFragment associationDetailsFragment = new AssociationDetailsFragment(newAssociation, category);
            ((FragmentActivity) loadingView.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_asso, associationDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Association association = listAssos.get(position);

        holder.nameAssoTV.setText(association.getName());
        holder.catAssoTV.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return listAssos.size();
    }


}

