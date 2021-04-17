package com.example.jetpackcomponentsapp.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.model.CustomModel;

import java.util.ArrayList;
import java.util.List;

public class CustomViewPagerAdapter extends RecyclerView.Adapter<BaseViewPagerHolder> {

    static private final String TAG = CustomViewPagerAdapter.class.getSimpleName();

    private List<CustomModel> list = new ArrayList<CustomModel>();

    public CustomViewPagerAdapter() {

    }

    public CustomViewPagerAdapter(List<CustomModel> list) {
        setItems(list);
    }

    @NonNull
    @Override
    public BaseViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder($parent,$viewType)");
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.cell_custom, parent, false);
        return new CustomViewPagerViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewPagerHolder holder, int position) {
        holder.bindDataToViewHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void setItems(List<CustomModel> list) {
        this.list.clear();
        this.list.addAll(list);
    }
}