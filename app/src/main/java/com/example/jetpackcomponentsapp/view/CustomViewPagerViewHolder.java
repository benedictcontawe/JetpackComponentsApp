package com.example.jetpackcomponentsapp.view;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.model.CustomModel;

public class CustomViewPagerViewHolder extends BaseViewPagerHolder {

    static private final String TAG = CustomViewPagerViewHolder.class.getSimpleName();
    //region cell_custom
    private final AppCompatImageView image;
    private final AppCompatTextView text;
    //endregion
    public CustomViewPagerViewHolder(Context context, View itemView) {
        super(context, itemView);
        Log.d(TAG, "constructor");
        image = itemView.findViewById(R.id.image);
        text = itemView.findViewById(R.id.text);
    }

    @Override
    public void bindDataToViewHolder(CustomModel customModel, int position) {
        image.setImageResource(customModel.icon);
        text.setText(customModel.name);
    }
}