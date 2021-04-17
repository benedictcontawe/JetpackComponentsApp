package com.example.jetpackcomponentsapp.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpackcomponentsapp.R;

public class IndicatorAdapter extends RecyclerView.Adapter<IndicatorViewHolder> {

    static private final String TAG = IndicatorAdapter.class.getSimpleName();
    static private final int SelectedIndicator = 0;
    static private final int UnSelectedLargeIndicator = 1;
    static private final int UnSelectedMediumIndicator = 2;
    static private final int UnSelectedSmallIndicator = 3;

    private int size;
    private int selected;

    public IndicatorAdapter() {
        size = 0;
        selected = 0;
    }

    public IndicatorAdapter(int size) {
        this.size = size;
        selected = 0;
    }

    public IndicatorAdapter(int size, int selected) {
        this.size = size;
        this.selected = selected;
    }

    @NonNull
    @Override
    public IndicatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder(" + parent + "," + viewType + ")");
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view;
        switch (viewType) {
            case IndicatorAdapter.SelectedIndicator:
                view = layoutInflater.inflate(R.layout.cell_circle_selected, parent, false);
                break;
            case IndicatorAdapter.UnSelectedLargeIndicator:
                view = layoutInflater.inflate(R.layout.cell_circle_large, parent, false);
                break;
            case IndicatorAdapter.UnSelectedMediumIndicator:
                view = layoutInflater.inflate(R.layout.cell_circle_medium, parent, false);
                break;
            case IndicatorAdapter.UnSelectedSmallIndicator:
                view = layoutInflater.inflate(R.layout.cell_circle_small, parent, false);
                break;
            default:
                view = layoutInflater.inflate(R.layout.cell_circle_large, parent, false);
                break;
        }
        return new IndicatorViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorViewHolder holder, int position) {
        holder.bindDataToViewHolder();
    }

    @Override
    public int getItemCount() {
        return this.size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == selected) return IndicatorAdapter.SelectedIndicator;
        else if (true) return IndicatorAdapter.UnSelectedLargeIndicator;
        else if (true) return IndicatorAdapter.UnSelectedMediumIndicator;
        else if (true) return IndicatorAdapter.UnSelectedSmallIndicator;
        else return super.getItemViewType(position);
    }

    public void setSelected(int selected) {
        notifyItemChanged(this.selected);
        this.selected = selected;
        notifyItemChanged(selected);
    }
}