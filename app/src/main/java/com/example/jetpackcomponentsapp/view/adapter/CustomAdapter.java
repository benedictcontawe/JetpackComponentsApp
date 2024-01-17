package com.example.jetpackcomponentsapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.CustomBinder;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.view.CustomListeners;
import com.example.jetpackcomponentsapp.view.holder.CustomViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private final CustomListeners customListeners;

    private CustomBinder customBinder;


    private final List<CustomModel> list = new ArrayList<>();

    public CustomAdapter(CustomListeners customListeners) {
        this.customListeners = customListeners;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        customBinder = DataBindingUtil.inflate (
            LayoutInflater.from(parent.getContext()), R.layout.cell_sample, parent, false
        );
        return new CustomViewHolder(customListeners, customBinder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        customBinder.setCustomModel(list.get(position));
        holder.bindDataToViewHolder(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<CustomModel> items) {
        list.clear();
        list.addAll(items);
    }
}