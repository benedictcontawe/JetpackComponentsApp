package com.example.jetpackcomponentsapp.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.jetpackcomponentsapp.R;

public class IndicatorViewHolder extends BaseViewHolder {

    static private final String TAG = IndicatorViewHolder.class.getSimpleName();
    //region cell_circle
    private final AppCompatImageView image;
    //endregion
    public IndicatorViewHolder(Context context, View itemView) {
        super(context, itemView);
        Log.d(TAG, "constructor");
        image = itemView.findViewById(R.id.image);
    }

    public void bindDataToViewHolder() {

    }
}