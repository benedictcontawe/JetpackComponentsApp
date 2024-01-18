package com.example.jetpackcomponentsapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jetpackcomponentsapp.R;
import com.example.jetpackcomponentsapp.databinding.PrimitiveBinder;
import com.example.jetpackcomponentsapp.view.model.PrimitiveViewModel;

public class PrimitiveActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = PrimitiveActivity.class.getSimpleName();
    public static Intent newIntent(Context context) {
        return new Intent(context, PrimitiveActivity.class).setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
    }
    private PrimitiveBinder binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_primitive);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_primitive);
        if (savedInstanceState == null) {
            PrimitiveViewModel viewModel = new ViewModelProvider(this).get(PrimitiveViewModel.class);
            binding.setViewModel(viewModel);
            binding.setLifecycleOwner(this);
            observeBoolean();
            observeString();
            observeInt();
            observeDouble();
        }
        binding.buttonBoolean.setOnClickListener(this);
        binding.buttonString.setOnClickListener(this);
        binding.buttonInteger.setOnClickListener(this);
        binding.buttonDouble.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.getViewModel().fetchData();
    }

    //region Observer Methods
    private void observeBoolean() {
        binding.getViewModel().observeBoolean().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                Log.d(TAG,"observeBoolean() $value");
                binding.labelBoolean.setText(value.toString());
            }
        });
    }

    private void observeString() {
        binding.getViewModel().observeString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                Log.d(TAG,"observeString() $value");
                binding.labelString.setText(value);
            }
        });
    }

    private void observeInt() {
        binding.getViewModel().observeInt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                Log.d(TAG,"observeInt() $value");
                binding.labelInteger.setText(value.toString());
            }
        });
    }

    private void observeDouble() {
        binding.getViewModel().observeDouble().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double value) {
                Log.d(TAG,"observeInt() $value");
                binding.labelDouble.setText(value.toString());
            }
        });
    }
    //endregion
    @Override
    public void onClick(View view) {
        if (view.getId() == binding.buttonBoolean.getId()) {
            binding.getViewModel().update (
                binding.checkBoxBoolean.isChecked()
            );
        } else if (view.getId() == binding.buttonString.getId()) {
            binding.getViewModel().update (
                binding.editTextString.getText().toString()
            );
        } else if (view.getId() == binding.buttonInteger.getId()) {
            binding.getViewModel().update (
                Integer.valueOf(binding.editTextInteger.getText().toString())
            );
        } else if (view.getId() == binding.buttonDouble.getId()) {
            //Toast.makeText(this,"Double Not Supported!",Toast.LENGTH_SHORT).show();
            binding.getViewModel().update (
                Double.valueOf(binding.editTextDouble.getText().toString())
            );
        }
    }
}