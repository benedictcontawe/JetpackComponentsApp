package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackcomponentsapp.databinding.MainBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    private var customSpinnerItemList = mutableListOf<CustomSpinnerModel>()
    private val names = arrayOf("A", "B", "C", "D", "E", "F", "G")
    private val icons = intArrayOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)

    private var isSpinnerTouch = false
    private var isCustomSpinnerTouch : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel //binding.viewModel = viewModel
        //binding.setLifecycleOwner(this)

        setSpinnerAdapter()
        setLiveDataObservers()
        setEventListeners()
    }

    private fun setSpinnerAdapter(){
        val spinnerAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, names)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSendData.adapter = spinnerAdapter


        for (i in 0 until names.size){
            customSpinnerItemList.add(CustomSpinnerModel(names[i],icons[i]))
        }
        val customAdapter : CustomAdapter = CustomAdapter(this@MainActivity, R.layout.custom_spinner_items, customSpinnerItemList)
        spinnerCustomSendData.adapter = customAdapter
    }

    private fun setLiveDataObservers(){
        val stringMediatorLiveData : MediatorLiveData<String> = viewModel.stringMediatorLiveData
        stringMediatorLiveData.addSource(viewModel.buttonLiveData) { stringMediatorLiveData.value = "${it}" }
        stringMediatorLiveData.addSource(viewModel.switchOnLiveData) { stringMediatorLiveData.value = "$it" }
        stringMediatorLiveData.addSource(viewModel.switchOffLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.toggleButtonOnLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.toggleButtonOffLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.radioButtonOnLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.radioButtonOffLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.checkBoxOnLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.checkBoxOffLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.spinnerLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.customSpinnerLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.ratingBarLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.seekBarLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.addSource(viewModel.seekBarDiscreteLiveData) { stringMediatorLiveData.value = it }
        stringMediatorLiveData.observe(this, object : Observer<String> {
            override fun onChanged(string: String) {
                binding.textResult.text = string
            }

        })

        val progressMediatorLiveData : MediatorLiveData<Int> = viewModel.progressMediatorLiveData
        progressMediatorLiveData.addSource(viewModel.seekBarProgressLiveData) { progressMediatorLiveData.value = it }
        progressMediatorLiveData.addSource(viewModel.seekBarDiscreteProgressLiveData) { progressMediatorLiveData.value = it }
        progressMediatorLiveData.observe(this, object : Observer<Int> {
            override fun onChanged(int: Int) {
                binding.progressBarResult.progress = int
            }

        })
    }

    private fun setEventListeners(){
        buttonSendData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                binding.viewModel?.buttonLiveData!!.value = "Button Was Clicked"
            }

        })

        switchSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.switchOnLiveData!!.setValue("Switch is On")
                } else if (isChecked == false) {
                    binding.viewModel?.switchOffLiveData!!.value = "Switch is Off"
                }
            }
        })

        toggleButtonSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView : CompoundButton , isChecked : Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.toggleButtonOnLiveData!!.setValue("Toggle Button is On")
                }
                else if (isChecked == false) {
                    binding.viewModel?.toggleButtonOffLiveData!!.value = "Toggle Button is Off"
                }
            }
        })

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                when (findViewById<View>(checkedId).id) {
                    R.id.radioOn -> {
                        binding.viewModel?.radioButtonOnLiveData!!.setValue("Radio Button is On of your selected Radio Group")
                    }
                    R.id.radioOff -> {
                        binding.viewModel?.radioButtonOffLiveData!!.setValue("Radio Button is Off of your selected Radio Group")
                    }
                }
            }
        })

        checkboxSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.checkBoxOnLiveData!!.setValue("Check Box is On")
                    checkboxSendData.text = "On"
                }
                else if (isChecked == false) {
                    binding.viewModel?.checkBoxOffLiveData!!.value = "Check Box is Off"
                    checkboxSendData.text = "Off"
                }
            }

        })

        spinnerSendData.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                isSpinnerTouch = true
                return false
            }
        })

        spinnerSendData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isSpinnerTouch) {
                    binding.viewModel?.spinnerLiveData!!.value = "Spinner value selected is ${names[position]}"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.viewModel?.spinnerLiveData!!.value = "Spinner Nothing selected"

            }
        }

        spinnerCustomSendData.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                isCustomSpinnerTouch = true
                return false
            }

        })

        spinnerCustomSendData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isCustomSpinnerTouch) {
                    binding.viewModel?.customSpinnerLiveData!!.value = "Customize Spinner value selected is " + names[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.viewModel?.customSpinnerLiveData!!.value = ""
            }
        }

        ratingBarSendData.onRatingBarChangeListener = object :  RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                binding.viewModel?.ratingBarLiveData!!.value = "Rating Bar value is ${rating}"
            }
        }

        seekBarSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.viewModel?.seekBarProgressLiveData!!.value = progress
                binding.viewModel?.seekBarLiveData!!.value = "Seek Bar Value selected is " + progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBarDiscreteSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.viewModel?.seekBarDiscreteProgressLiveData!!.value = progress
                binding.viewModel?.seekBarDiscreteLiveData!!.value = "Customize Seek Bar Value selected is " + progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        seekBarSendData.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                seekBarSendData.thumb = resources.getDrawable(
                        when(hasFocus){
                            true -> {
                                R.drawable.ic_seeker_thumb_selected
                            }
                            false -> {
                                R.drawable.ic_seeker_thumb_unselected
                            }
                        }
                )
            }

        }

        seekBarDiscreteSendData.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                seekBarDiscreteSendData.thumb = resources.getDrawable(
                        when(hasFocus){
                            true -> {
                                R.drawable.ic_lever_selected
                            }
                            false -> {
                                R.drawable.ic_lever_unselected
                            }
                        }
                )
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        buttonSendData.setOnClickListener(null)
        switchSendData.setOnCheckedChangeListener(null)
        toggleButtonSendData.setOnCheckedChangeListener(null)
        radioGroup.setOnCheckedChangeListener(null)
        checkboxSendData.setOnCheckedChangeListener(null)
        spinnerSendData.onItemSelectedListener = null
        spinnerCustomSendData.onItemSelectedListener = null
        ratingBarSendData.onRatingBarChangeListener = null
    }
}