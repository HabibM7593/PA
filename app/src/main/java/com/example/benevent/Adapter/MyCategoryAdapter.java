package com.example.benevent.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.benevent.Models.Category;
import com.example.benevent.R;
import com.example.benevent.ui.fragment.AssociationFragment;

import java.util.List;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder> {

    private List<Category> listCategory;

    public MyCategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView nameCategoryTV;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            nameCategoryTV = view.findViewById(R.id.name_category);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = listCategory.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = listCategory.get(position);
                AssociationFragment aFragment = new AssociationFragment(category.getName());

                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_category, aFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.nameCategoryTV.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
