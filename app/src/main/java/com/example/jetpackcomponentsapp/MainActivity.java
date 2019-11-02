package com.example.jetpackcomponentsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;

    private String[] names ={"A","B","C","D","E","F","G"};
    private int icons[] = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    private Button buttonSendData;
    private CheckBox checkboxSendData;
    private ProgressBar progressBarResult;
    private RadioGroup radioGroup;
    private RadioButton radioOn,radioOff;
    private RatingBar ratingBarSendData;
    private SeekBar seekBarSendData,seekBarDiscreteSendData;
    private Spinner spinnerSendData,spinnerCustomSendData;
    private SwitchCompat switchSendData; //Use SwitchCompat instead of using Switch
    private TextView textResult;
    private ToggleButton toggleButtonSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();

        findViewId();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setSpinnerAdapter();
        setLiveDataObservers();
        setEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.getViewModel().setData("A");
    }

    private void findViewId(){
        textResult = (TextView) findViewById(R.id.textResult);
        buttonSendData = (Button) findViewById(R.id.buttonSendData);
        switchSendData = (SwitchCompat) findViewById(R.id.switchSendData);
        toggleButtonSendData = (ToggleButton) findViewById(R.id.toggleBtnSendData);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioOn = (RadioButton) findViewById(R.id.radioOn);
        radioOff = (RadioButton) findViewById(R.id.radioOff);
        checkboxSendData = (CheckBox) findViewById(R.id.checkboxSendData);
        spinnerSendData = (Spinner) findViewById(R.id.spinnerSendData);
        spinnerCustomSendData = (Spinner) findViewById(R.id.spinnerCustomSendData);
        ratingBarSendData = (RatingBar) findViewById(R.id.ratingBarSendData);
        seekBarSendData = (SeekBar) findViewById(R.id.seekBarSendData);
        seekBarDiscreteSendData = (SeekBar) findViewById(R.id.seekBarDiscreteSendData);
        progressBarResult = (ProgressBar) findViewById(R.id.progressBarResult);
    }

    private void setSpinnerAdapter() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSendData.setAdapter(spinnerAdapter);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),icons, names);
        spinnerCustomSendData.setAdapter(customAdapter);
    }

    private void setLiveDataObservers() {
        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                binding.textResult.setText(string);
            }
        });

        viewModel.getProgressData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBarResult.setProgress(integer);
            }
        });

    }

    private void setEventListeners(){
        buttonSendData.setOnClickListener(new View.OnClickListener() {
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

        toggleButtonSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        spinnerSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.getViewModel().setData("Spinner value selected is " + names[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().setData("Spinner Nothing selected");
            }
        });

        spinnerCustomSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.getViewModel().setData("Customize Spinner value selected is " + names[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().setData("Customize Spinner Nothing selected");
            }
        });

        ratingBarSendData.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                binding.getViewModel().setData("Rating Bar value is " + String.valueOf(rating));
            }
        });

        seekBarSendData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.getViewModel().setProgressData(progress);
                binding.getViewModel().setData("Seek Bar Value selected is " + progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSendData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus){
                    //Toast.makeText(getBaseContext(),"On Enter Focus",Toast.LENGTH_SHORT).show();
                    seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_selected));
                }
                else {
                    //Toast.makeText(getBaseContext(),"On Leave Focus",Toast.LENGTH_SHORT).show();
                    seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_seeker_thumb_unselected));
                }
            }
        });

        seekBarDiscreteSendData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.getViewModel().setProgressData(progress);
                binding.getViewModel().setData("Customize Seek Bar Value selected is " + progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarDiscreteSendData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus){
                    //Toast.makeText(getBaseContext(),"On Enter Focus",Toast.LENGTH_SHORT).show();
                    seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_selected));
                }
                else {
                    //Toast.makeText(getBaseContext(),"On Leave Focus",Toast.LENGTH_SHORT).show();
                    seekBarSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_unselected));
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewModel.getData().observe(this, null);
        buttonSendData.setOnClickListener(null);
        switchSendData.setOnCheckedChangeListener(null);
        toggleButtonSendData.setOnCheckedChangeListener(null);
        radioGroup.setOnCheckedChangeListener(null);
        checkboxSendData.setOnCheckedChangeListener(null);
        spinnerSendData.setOnItemSelectedListener(null);
        spinnerCustomSendData.setOnItemSelectedListener(null);
        ratingBarSendData.setOnRatingBarChangeListener(null);

    }

}
