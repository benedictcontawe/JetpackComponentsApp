package com.example.jetpackcomponentsapp;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

public class MainActivity extends AppCompatActivity implements OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, RatingBar.OnRatingBarChangeListener, SeekBar.OnSeekBarChangeListener, OnFocusChangeListener {

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

        binding.getViewModel().setData("Test");
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
        buttonSendData.setOnClickListener(this);
        switchSendData.setOnCheckedChangeListener(this);
        toggleButtonSendData.setOnCheckedChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        checkboxSendData.setOnCheckedChangeListener(this);
        spinnerSendData.setOnItemSelectedListener(this);
        spinnerCustomSendData.setOnItemSelectedListener(this);
        ratingBarSendData.setOnRatingBarChangeListener(this);
        seekBarSendData.setOnSeekBarChangeListener(this);
        seekBarDiscreteSendData.setOnSeekBarChangeListener(this);
        seekBarSendData.setOnFocusChangeListener(this);
        seekBarDiscreteSendData.setOnFocusChangeListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonSendData.getId()){
            binding.getViewModel().setData("Button Was Clicked");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final int x = switchSendData.getId();

        if (buttonView.getId() == switchSendData.getId()){
            if (isChecked == true) {
                binding.getViewModel().setData("Switch is On");
            }
            else if (isChecked == false) {
                binding.getViewModel().setData("Switch is Off");
            }
        }
        else if(buttonView.getId() == toggleButtonSendData.getId()){
            if (isChecked == true) {
                binding.getViewModel().setData("Toggle Button is On");
            }
            else if (isChecked == false) {
                binding.getViewModel().setData("Toggle Button is Off");
            }
        }
        else if(buttonView.getId() == checkboxSendData.getId()){
            if (isChecked == true) {
                binding.getViewModel().setData("Check Box is On");
                checkboxSendData.setText("On");
            }
            else if (isChecked == false) {
                binding.getViewModel().setData("Check Box is Off");
                checkboxSendData.setText("Off");
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == radioGroup.getId()){
            if (checkedId == radioOn.getId()){
                binding.getViewModel().setData("Radio Button is On of your selected Radio Group");
            }
            else if (checkedId == radioOff.getId()){
                binding.getViewModel().setData("Radio Button is Off of your selected Radio Group");
            }
        }
    }
    //region AdapterView.OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spinnerSendData.getId()){
            binding.getViewModel().setData("Spinner value selected is " + names[position]);
        }
        else if(parent.getId() == spinnerCustomSendData.getId()){
            binding.getViewModel().setData("Customize Spinner value selected is " + names[position]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == spinnerSendData.getId()) {
            binding.getViewModel().setData("Spinner Nothing selected");
        }
        else if(parent.getId() == spinnerCustomSendData.getId()){
            binding.getViewModel().setData("Customize Spinner Nothing selected");
        }
    }
    //endregion
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (ratingBar.getId() == ratingBarSendData.getId()){
            binding.getViewModel().setData("Rating Bar value is " + String.valueOf(rating));
        }
    }
    //region SeekBar.OnSeekBarChangeListener
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == seekBarSendData.getId()) {
            binding.getViewModel().setProgressData(progress);
            binding.getViewModel().setData("Seek Bar Value selected is " + progress);
        }
        else if (seekBar.getId() == seekBarDiscreteSendData.getId()){
            binding.getViewModel().setProgressData(progress);
            binding.getViewModel().setData("Customize Seek Bar Value selected is " + progress);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onFocusChange(View view, boolean isFocus) {
        if (view.getId() == seekBarSendData.getId()) {
            seekBarSendData.setThumb(getResources().getDrawable(isFocus ? R.drawable.ic_seeker_thumb_selected : R.drawable.ic_seeker_thumb_unselected));
        }
        else if (view.getId() == seekBarDiscreteSendData.getId()){
            seekBarDiscreteSendData.setThumb(getResources().getDrawable(isFocus ? R.drawable.ic_lever_selected : R.drawable.ic_lever_unselected));
        }
    }
    //endregion
}
