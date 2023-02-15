package com.example.newyorkschools.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newyorkschools.R;
import com.example.newyorkschools.data.School;

import java.util.List;

public class RecyclerAdapter extends
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<School> schools;
    SchoolClickHandler clickHandler;

    interface SchoolClickHandler {
        void onSchoolClicked(School school);
    }
    public RecyclerAdapter(List<School> schools, SchoolClickHandler clickHandler) {
        this.schools = schools;
        this.clickHandler = clickHandler;
    }

    public void updateSchools(List<School> schools) {
        this.schools = schools;
        // Since we aren't doing partial data fetches (the API always sends down the full list of
        // schools), we can just notify that all data has changed.
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.school_list_item, parent, false);

        return new ViewHolder(contactView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (schools == null || position >= schools.size()) return;

        School school = schools.get(position);
        holder.nameView.setText(school.school_name);
        holder.dbnView.setText(school.dbn);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onSchoolClicked(school);
            }
        });
    }
    @Override
    public int getItemCount() {
        return schools.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View root;
        public TextView nameView;
        public TextView dbnView;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.school_item);
            nameView = itemView.findViewById(R.id.school_name);
            dbnView = itemView.findViewById(R.id.school_dbn);
        }
    }
}