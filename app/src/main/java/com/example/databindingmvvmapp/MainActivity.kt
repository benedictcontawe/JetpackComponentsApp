package com.example.databindingmvvmapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.databindingmvvmapp.databinding.MainBinder
import kotlinx.android.synthetic.main.activity_main.*

public class MainActivity : AppCompatActivity(){

    private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    private var customSpinnerItemList = mutableListOf<CustomSpinnerModel>()
    private val names = arrayOf("A", "B", "C", "D", "E", "F", "G")
    private val icons = intArrayOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.setViewModel(viewModel) //binding.viewModel = viewModel
        //binding.setLifecycleOwner(this)

    }

    override fun onStart() {
        super.onStart()

        setSpinnerAdapter()
        setLiveDataObservers()
        setEventListeners()
    }

    override fun onResume() {
        super.onResume()

        binding.getViewModel()?.setData("Test")
    }

    private fun setSpinnerAdapter(){
        val spinnerAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, names)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSendData.setAdapter(spinnerAdapter)


        for (i in 0 until names.size){
            customSpinnerItemList.add(CustomSpinnerModel(names[i],icons[i]))
        }
        val customAdapter : CustomAdapter = CustomAdapter(this@MainActivity, R.layout.custom_spinner_items, customSpinnerItemList)
        spinnerCustomSendData.setAdapter(customAdapter)
    }

    private fun setLiveDataObservers(){
        viewModel.getData().observe(this, object : Observer<String> {
            override fun onChanged(string: String) {
                binding.textResult.setText(string)
            }

        })

        viewModel.getProgressData().observe(this, object : Observer<Int> {
            override fun onChanged(int: Int) {
                binding.progressBarResult.setProgress(int)
            }

        })
    }

    private fun setEventListeners(){
        buttonSendData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                binding.getViewModel()?.setData("Button Was Clicked")
            }

        })

        switchSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Switch is On")
                } else if (isChecked == false) {
                    binding.getViewModel()?.setData("Switch is Off")
                }
            }
        })

        toggleButtonSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView : CompoundButton , isChecked : Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Toggle Button is On")
                }
                else if (isChecked == false) {
                    binding.getViewModel()?.setData("Toggle Button is Off")
                }
            }
        })

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                when (findViewById<View>(checkedId).getId()) {
                    R.id.radioOn -> {
                        binding.getViewModel()?.setData("Radio Button is On of your selected Radio Group")
                    }
                    R.id.radioOff -> {
                        binding.getViewModel()?.setData("Radio Button is Off of your selected Radio Group")
                    }
                }
            }
        })

        checkboxSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Check Box is On")
                    checkboxSendData.setText("On")
                }
                else if (isChecked == false) {
                    binding.getViewModel()?.setData("Check Box is Off")
                    checkboxSendData.setText("Off")
                }
            }

        })

        spinnerSendData.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.getViewModel()?.setData("Spinner value selected is " + names[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.getViewModel()?.setData("Spinner Nothing selected")
            }
        })

        spinnerCustomSendData.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.getViewModel()?.setData("Customize Spinner value selected is " + names[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.getViewModel()?.setData("Customize Spinner Nothing selected")
            }
        })

        ratingBarSendData.setOnRatingBarChangeListener(object :  RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                binding.getViewModel()?.setData("Rating Bar value is ${rating}")
            }
        })

        seekBarSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.getViewModel()?.setProgressData(progress);
                binding.getViewModel()?.setData("Seek Bar Value selected is " + progress);
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBarDiscreteSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.getViewModel()?.setProgressData(progress)
                binding.getViewModel()?.setData("Customize Seek Bar Value selected is " + progress)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBarSendData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                seekBarSendData.setThumb(getResources().getDrawable(
                    when(hasFocus){
                        true -> {
                            R.drawable.ic_seeker_thumb_selected
                        }
                        false -> {
                            R.drawable.ic_seeker_thumb_unselected
                        }
                    }
                ))
            }

        })

        seekBarDiscreteSendData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                seekBarDiscreteSendData.setThumb(getResources().getDrawable(
                        when(hasFocus){
                            true -> {
                                R.drawable.ic_lever_selected
                            }
                            false -> {
                                R.drawable.ic_lever_unselected
                            }
                        }
                ))
            }

        })
    }

    override fun onStop() {
        super.onStop()

        viewModel.getData().observe(this, null!!)
        buttonSendData.setOnClickListener(null)
        switchSendData.setOnCheckedChangeListener(null)
        toggleButtonSendData.setOnCheckedChangeListener(null)
        radioGroup.setOnCheckedChangeListener(null)
        checkboxSendData.setOnCheckedChangeListener(null)
        spinnerSendData.setOnItemSelectedListener(null)
        spinnerCustomSendData.setOnItemSelectedListener(null)
        ratingBarSendData.setOnRatingBarChangeListener(null)
    }
}