package com.example.jetpackcomponentsapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();

        setLiveDataObservers();
        setEventListeners();
    }

    private void setLiveDataObservers() {
        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                binding.textResult.setText(string);
            }
        });
    }

    private void setEventListeners(){
        binding.buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().setData(
                        new CustomModel(
                                binding.editTextFirstName.getText().toString(),
                                binding.editTextLastName.getText().toString()
                        )
                );
            }
        });
    }
}