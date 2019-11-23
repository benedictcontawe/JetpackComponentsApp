package com.example.jetpackcomponentsapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.jetpackcomponentsapp.databinding.MainBinder;

public class MainActivity extends AppCompatActivity {

    private MainBinder binding;
    private MainViewModel viewModel;

    private String[] names ={"A","B","C","D","E","F","G"};
    private int[] icons = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    private Boolean isSpinnerTouch = false, isCustomSpinnerTouch = false;

    private Observer<String> stringObserver = new Observer<String>() {
        @Override
        public void onChanged(String string) {
            binding.textResult.setText(string);
        }
    };

    private Observer<Integer> integerObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            binding.progressBarResult.setProgress(integer);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.setViewModel(viewModel);
        //binding.setLifecycleOwner();

        setSpinnerAdapter();
        setLiveDataObservers();
        setEventListeners();
    }

    private void setSpinnerAdapter() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSendData.setAdapter(spinnerAdapter); //spinnerSendData.setAdapter(spinnerAdapter);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),icons, names);
        binding.spinnerCustomSendData.setAdapter(customAdapter);
    }

    private void setLiveDataObservers() {
        MediatorLiveData<String> stringMediatorLiveData = viewModel.stringMediatorLiveData;
        stringMediatorLiveData.addSource(viewModel.buttonLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.switchOnLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.switchOffLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.toggleButtonOnLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.toggleButtonOffLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.radioButtonOnLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.radioButtonOffLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.checkBoxOnLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.checkBoxOffLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.spinnerLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.customSpinnerLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.ratingBarLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.seekBarLiveData,stringObserver);
        stringMediatorLiveData.addSource(viewModel.seekBarDiscreteLiveData,stringObserver);
        stringMediatorLiveData.observe(this, stringObserver);

        MediatorLiveData<Integer> progressMediatorLiveData = viewModel.progressMediatorLiveData;
        progressMediatorLiveData.addSource(viewModel.seekBarProgressLiveData,integerObserver);
        progressMediatorLiveData.addSource(viewModel.seekBarDiscreteProgressLiveData,integerObserver);
        progressMediatorLiveData.observe(this, integerObserver);
    }

    private void setEventListeners(){
        binding.buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().buttonLiveData.setValue("Button Was Clicked");
            }
        });

        binding.switchSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().switchOnLiveData.setValue("Switch is On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().switchOffLiveData.setValue("Switch is Off");
                }
            }
        });

        binding.toggleButtonSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().toggleButtonOnLiveData.setValue("Toggle Button is On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().toggleButtonOffLiveData.setValue("Toggle Button is Off");
                }
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (findViewById(checkedId).getId()){
                    case R.id.radioOn:
                        binding.getViewModel().radioButtonOnLiveData.setValue("Radio Button is On of your selected Radio Group");
                        break;
                    case R.id.radioOff:
                        binding.getViewModel().radioButtonOffLiveData.setValue("Radio Button is Off of your selected Radio Group");
                        break;
                }
            }
        });

        binding.checkboxSendData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    binding.getViewModel().checkBoxOnLiveData.setValue("Check Box is On");
                    binding.checkboxSendData.setText("On");
                }
                else if (isChecked == false) {
                    binding.getViewModel().checkBoxOffLiveData.setValue("Check Box is Off");
                    binding.checkboxSendData.setText("Off");
                }
            }
        });

        binding.spinnerSendData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isSpinnerTouch = true;
                return false;
            }
        });

        binding.spinnerSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerTouch) {
                    binding.getViewModel().spinnerLiveData.setValue("Spinner value selected is " + names[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().spinnerLiveData.setValue("Spinner Nothing selected");
            }
        });

        binding.spinnerCustomSendData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isCustomSpinnerTouch = true;
                return false;
            }
        });

        binding.spinnerCustomSendData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isCustomSpinnerTouch) {
                    binding.getViewModel().customSpinnerLiveData.setValue("Customize Spinner value selected is " + names[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.getViewModel().customSpinnerLiveData.setValue("Customize Spinner Nothing selected");
            }
        });

        binding.ratingBarSendData.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                binding.getViewModel().ratingBarLiveData.setValue("Rating Bar value is " + rating);
            }
        });

        binding.seekBarSendData.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.getViewModel().seekBarProgressLiveData.setValue(progress);
                binding.getViewModel().seekBarLiveData.setValue("Seek Bar Value selected is " + progress);
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
                binding.getViewModel().seekBarDiscreteProgressLiveData.setValue(progress);
                binding.getViewModel().seekBarDiscreteLiveData.setValue("Customize Seek Bar Value selected is " + progress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBarDiscreteSendData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (isFocus){
                    //Toast.makeText(getBaseContext(),"On Enter Focus",Toast.LENGTH_SHORT).show();
                    binding.seekBarDiscreteSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_selected));
                }
                else {
                    //Toast.makeText(getBaseContext(),"On Leave Focus",Toast.LENGTH_SHORT).show();
                    binding.seekBarDiscreteSendData.setThumb(getResources().getDrawable(R.drawable.ic_lever_unselected));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
