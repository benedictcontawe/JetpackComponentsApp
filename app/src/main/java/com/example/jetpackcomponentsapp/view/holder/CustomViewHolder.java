package com.example.jetpackcomponentsapp.view.holder;

import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.CustomBinder;
import com.example.jetpackcomponentsapp.model.CustomModel;
import com.example.jetpackcomponentsapp.view.CustomListeners;

public class CustomViewHolder extends BaseViewHolder {

    private final CustomBinder binder;

    public CustomViewHolder(CustomListeners listeners, CustomBinder customBinder ) {
        super(listeners,customBinder .getRoot());
        this.binder = customBinder;
    }

    @Override
    public void bindDataToViewHolder(final CustomModel model, final int position) {
        binder.setCustomModel(model);
        loadPhoto();
        binder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onUpdate(model, position);
            }
        });
        binder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onDelete(model, position);
            }
        });
    }

    private void loadPhoto() {
        Glide.with(getContext())
            .asBitmap()
            .error(R.drawable.ic_image)
            .placeholder(R.drawable.animation_loading)
            .priority(Priority.NORMAL)
            .load(binder.getCustomModel().icon)
            .into(binder.imageView);
    }
}