package com.example.jetpackcomponentsapp.view.holder;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.view.CustomListeners;

abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    /**Main */
    private final CustomListeners listeners;

    public BaseViewHolder(CustomListeners listeners, @NonNull View itemView) {
        super(itemView);
        this.listeners = listeners;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public CustomListeners getListener() {
        return listeners;
    }

    abstract public void bindDataToViewHolder(CustomModel item, int position);
}