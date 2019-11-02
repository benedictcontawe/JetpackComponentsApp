package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackcomponentsapp.databinding.MainBinder

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
        setEventListeners()
    }

    override fun onResume() {
        super.onResume()

        binding.getViewModel()?.setData("Test")
        binding.textResult.setText(binding.getViewModel().getData())
    }

    private fun setSpinnerAdapter(){
        val spinnerAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, names)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSendData.setAdapter(spinnerAdapter)


        for (i in 0 until names.size){
            customSpinnerItemList.add(CustomSpinnerModel(names[i],icons[i]))
        }
        val customAdapter : CustomAdapter = CustomAdapter(this@MainActivity, R.layout.custom_spinner_items, customSpinnerItemList)
        binding.spinnerCustomSendData.setAdapter(customAdapter)
    }

    private fun setEventListeners(){
        binding.buttonSendData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                binding.getViewModel()?.setData("Button Was Clicked")
                binding.textResult.setText(binding.getViewModel().getData())
            }

        })

        binding.switchSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Switch is On")
                    binding.textResult.setText(binding.getViewModel().getData())
                } else if (isChecked == false) {
                    binding.getViewModel()?.setData("Switch is Off")
                    binding.textResult.setText(binding.getViewModel().getData())
                }
            }
        })

        binding.toggleButtonSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView : CompoundButton , isChecked : Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Toggle Button is On")
                    binding.textResult.setText(binding.getViewModel().getData())
                }
                else if (isChecked == false) {
                    binding.getViewModel()?.setData("Toggle Button is Off")
                    binding.textResult.setText(binding.getViewModel().getData())
                }
            }
        })

        binding.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                when (findViewById<View>(checkedId).getId()) {
                    R.id.radioOn -> {
                        binding.getViewModel()?.setData("Radio Button is On of your selected Radio Group")
                        binding.textResult.setText(binding.getViewModel().getData())
                    }
                    R.id.radioOff -> {
                        binding.getViewModel()?.setData("Radio Button is Off of your selected Radio Group")
                        binding.textResult.setText(binding.getViewModel().getData())
                    }
                }
            }
        })

        binding.checkboxSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.getViewModel()?.setData("Check Box is On")
                    binding.textResult.setText(binding.getViewModel().getData())
                    binding.checkboxSendData.setText("On")
                }
                else if (isChecked == false) {
                    binding.getViewModel()?.setData("Check Box is Off")
                    binding.textResult.setText(binding.getViewModel().getData())
                    binding.checkboxSendData.setText("Off")
                }
            }

        })

        binding.spinnerSendData.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.getViewModel()?.setData("Spinner value selected is " + names[position])
                binding.textResult.setText(binding.getViewModel().getData())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.getViewModel()?.setData("Spinner Nothing selected")
                binding.textResult.setText(binding.getViewModel().getData())
            }
        })

        binding.spinnerCustomSendData.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.getViewModel()?.setData("Customize Spinner value selected is " + names[position])
                binding.textResult.setText(binding.getViewModel().getData())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.getViewModel()?.setData("Customize Spinner Nothing selected")
                binding.textResult.setText(binding.getViewModel().getData())
            }
        })

        binding.ratingBarSendData.setOnRatingBarChangeListener(object :  RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                binding.getViewModel()?.setData("Rating Bar value is ${rating}")
                binding.textResult.setText(binding.getViewModel().getData())
            }
        })

        binding.seekBarSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.getViewModel()?.setProgressData(progress)
                binding.progressBarResult.setProgress(binding.getViewModel().getProgressData()?:0)
                binding.getViewModel()?.setData("Seek Bar Value selected is " + progress)
                binding.textResult.setText(binding.getViewModel().getData())
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.seekBarDiscreteSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.getViewModel()?.setProgressData(progress)
                binding.progressBarResult.setProgress(binding.getViewModel().getProgressData()?:0)
                binding.getViewModel()?.setData("Customize Seek Bar Value selected is " + progress)
                binding.textResult.setText(binding.getViewModel().getData())
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.seekBarSendData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                binding.seekBarSendData.setThumb(getResources().getDrawable(
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

        binding.seekBarDiscreteSendData.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                binding.seekBarDiscreteSendData.setThumb(getResources().getDrawable(
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

        binding.buttonSendData.setOnClickListener(null)
        binding.switchSendData.setOnCheckedChangeListener(null)
        binding.toggleButtonSendData.setOnCheckedChangeListener(null)
        binding.radioGroup.setOnCheckedChangeListener(null)
        binding.checkboxSendData.setOnCheckedChangeListener(null)
        binding.spinnerSendData.setOnItemSelectedListener(null)
        binding.spinnerCustomSendData.setOnItemSelectedListener(null)
        binding.ratingBarSendData.setOnRatingBarChangeListener(null)
    }
}