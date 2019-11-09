package com.example.jetpackcomponentsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;

    private String[] names ={"A","B","C","D","E","F","G"};
    private int[] icons = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setSpinnerAdapter();
        setEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.getViewModel().setData("");
        binding.textResult.setText(binding.getViewModel().getData());
    }

    private void setSpinnerAdapter() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSendData.setAdapter(spinnerAdapter); //spinnerSendData.setAdapter(spinnerAdapter);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),icons, names);
        binding.spinnerCustomSendData.setAdapter(customAdapter);
    }

    private void setEventListeners(){
        binding.buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().setData("Button Was Clicked");
                binding.textResult.setText(binding.getViewModel().getData());
            }
        });

        binding.switchSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Switch is On");
                    binding.textResult.setText(binding.getViewModel().getData());
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Switch is Off");
                    binding.textResult.setText(binding.getViewModel().getData());
                }
            }
        });

        binding.toggleButtonSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Toggle Button is On");
                    binding.textResult.setText(binding.getViewModel().getData());
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Toggle Button is Off");
                    binding.textResult.setText(binding.getViewModel().getData());
                }
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (findViewById(checkedId).getId()){
                    case R.id.radioOn:
                        binding.getViewModel().setData("Radio Button is On of your selected Radio Group");
                        binding.textResult.setText(binding.getViewModel().getData());
                        break;
                    case R.id.radioOff:
                        binding.getViewModel().setData("Radio Button is Off of your selected Radio Group");
                        binding.textResult.setText(binding.getViewModel().getData());
                        break;
                }
            }
        });

        binding.checkboxSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().setData("Check Box is On");
                    binding.textResult.setText(binding.getViewModel().getData());
                    binding.checkboxSendData.setText("On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().setData("Check Box is Off");
                    binding.textResult.setText(binding.getViewModel().getData());
                    binding.checkboxSendData.setText("Off");
                }
            }
        });

        binding.spinnerSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.getViewModel().setData("Spinner value selected is " + names[position]);
                binding.textResult.setText(binding.getViewModel().getData());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().setData("Spinner Nothing selected");
                binding.textResult.setText(binding.getViewModel().getData());
            }
        });

        binding.spinnerCustomSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.getViewModel().setData("Customize Spinner value selected is " + names[position]);
                binding.textResult.setText(binding.getViewModel().getData());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().setData("Customize Spinner Nothing selected");
                binding.textResult.setText(binding.getViewModel().getData());
            }
        });

        binding.ratingBarSendData.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                binding.getViewModel().setData("Rating Bar value is " + rating);
                binding.textResult.setText(binding.getViewModel().getData());
            }
        });

        binding.seekBarSendData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.getViewModel().setProgressData(progress);
                binding.progressBarResult.setProgress(binding.getViewModel().getProgressData());
                binding.getViewModel().setData("Seek Bar Value selected is " + progress);
                binding.textResult.setText(binding.getViewModel().getData());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBarSendData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus){
                    //Toast.makeText(getBaseContext(),"On Enter Focus",Toast.LENGTH_SHORT).show();
                    binding.seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected));
                }
                else {
                    //Toast.makeText(getBaseContext(),"On Leave Focus",Toast.LENGTH_SHORT).show();
                    binding.seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_unselected));
                }
            }
        });

        binding.seekBarDiscreteSendData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.getViewModel().setProgressData(progress);
                binding.progressBarResult.setProgress(binding.getViewModel().getProgressData());
                binding.getViewModel().setData("Customize Seek Bar Value selected is " + progress);
                binding.textResult.setText(binding.getViewModel().getData());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBarDiscreteSendData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus){
                    binding.seekBarDiscreteSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_selected));
                }
                else {
                    binding.seekBarDiscreteSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_unselected));
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        binding.buttonSendData.setOnClickListener(null);
        binding.switchSendData.setOnCheckedChangeListener(null);
        binding.toggleButtonSendData.setOnCheckedChangeListener(null);
        binding.radioGroup.setOnCheckedChangeListener(null);
        binding.checkboxSendData.setOnCheckedChangeListener(null);
        binding.spinnerSendData.setOnItemSelectedListener(null);
        binding.spinnerCustomSendData.setOnItemSelectedListener(null);
        binding.ratingBarSendData.setOnRatingBarChangeListener(null);
    }

}