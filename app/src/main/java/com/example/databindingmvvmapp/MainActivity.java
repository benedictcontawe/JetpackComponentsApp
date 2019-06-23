package com.example.databindingmvvmapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.databindingmvvmapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;

    Button btnSendData;
    TextView txtResult;
    SwitchCompat switchSendData; //Use SwitchCompat instead of using Switch
    ToggleButton toggleBtnSendData;
    CheckBox checkboxSendData;
    RadioGroup radioGroup;
    RadioButton radioOn,radioOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();

        txtResult = (TextView) findViewById(R.id.txtResult);
        btnSendData = (Button) findViewById(R.id.btnSendData);
        switchSendData = (SwitchCompat) findViewById(R.id.switchSendData);
        toggleBtnSendData = (ToggleButton) findViewById(R.id.toggleBtnSendData);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioOn = (RadioButton) findViewById(R.id.radioOn);
        radioOff = (RadioButton) findViewById(R.id.radioOff);
        checkboxSendData = (CheckBox) findViewById(R.id.checkboxSendData);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.txtResult.setText(s);
            }
        });

        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().setData("Button Was Clicked");
            }
        });


        switchSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Switch is On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Switch is Off");
                }
            }
        });

        toggleBtnSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Toggle Button is On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Toggle Button is Off");
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (findViewById(checkedId).getId()){
                    case R.id.radioOn:
                        binding.getViewModel().setData("Radio Button is On of your selected Radio Group");
                    break;
                    case R.id.radioOff:
                        binding.getViewModel().setData("Radio Button is Off of your selected Radio Group");
                        break;
                }
            }
        });

        checkboxSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Check Box is On");
                    checkboxSendData.setText("On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Check Box is Off");
                    checkboxSendData.setText("Off");
                }
            }
        });


    }
}
