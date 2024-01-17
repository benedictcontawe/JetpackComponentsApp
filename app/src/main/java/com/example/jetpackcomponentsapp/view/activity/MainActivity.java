package com.example.jetpackcomponentsapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static Intent newIntent(Context context) {
        return new Intent(context, PrimitiveActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    private MainBinder binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.primitiveButton.setOnClickListener(this::onClick);
        binding.objectButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.primitiveButton.getId()) {
            startActivity(PrimitiveActivity.newIntent(this));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if (view.getId() == binding.objectButton.getId()) {
            startActivity(ObjectActivity.newIntent(this));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}