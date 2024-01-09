package com.example.jetpackcomponentsapp.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    static private final String TAG = BaseViewHolder.class.getSimpleName();

    protected final Context context;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        Log.d(TAG, "constructor");
    }

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        Log.d(TAG, "constructor");
    }
}