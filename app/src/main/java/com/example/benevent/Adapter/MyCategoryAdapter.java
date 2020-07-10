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
import com.example.benevent.Models.Event;
import com.example.benevent.R;
import com.example.benevent.ui.fragment.AssociationFragment;
import com.example.benevent.ui.fragment.EventDetailsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder> {

    private List<Category> listCategory;

    public List<Category> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public MyCategoryAdapter(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView nameCategoryTV;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            nameCategoryTV = v.findViewById(R.id.name_category);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_cell_category, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        ImageButton buttonDetailShow = v.findViewById(R.id.button_category_view);
        buttonDetailShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Category category = listCategory.get(vh.getAdapterPosition());
                AssociationFragment aFragment = new AssociationFragment();

                ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_category, aFragment)
                        .commit();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = listCategory.get(position);

        holder.nameCategoryTV.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
}
