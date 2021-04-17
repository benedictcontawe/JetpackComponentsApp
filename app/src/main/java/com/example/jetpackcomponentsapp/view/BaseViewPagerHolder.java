package com.example.jetpackcomponentsapp.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.jetpackcomponentsapp.model.CustomModel;

abstract public class BaseViewPagerHolder extends BaseViewHolder {

    static private final String TAG = BaseViewPagerHolder.class.getSimpleName();

    public BaseViewPagerHolder(Context context, View itemView) {
        super(context, itemView);
        Log.d(TAG, "constructor");
    }

    abstract public void bindDataToViewHolder(CustomModel customModel, int position);
}