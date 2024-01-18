package com.example.jetpackcomponentsapp.view.holder;

import android.view.View;

import com.example.jetpackcomponentsapp.databinding.CustomBinder;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.view.CustomListeners;

public class CustomViewHolder extends BaseViewHolder {

    private final CustomBinder binder;

    public CustomViewHolder(CustomListeners listeners, CustomBinder binder ) {
        super(listeners,binder .getRoot());
        this.binder = binder;
    }

    @Override
    public void bindDataToViewHolder(final CustomModel model, final int position) {
        binder.setCustomModel(model);
        binder.imageView.setImageResource(model.getIcon());
        binder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onUpdate(model,position);
            }
        });
        binder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onDelete(model,position);
            }
        });
    }
}