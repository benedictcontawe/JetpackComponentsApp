package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackcomponentsapp.databinding.MainBinder

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
        setEventListeners()
    }

    override fun onResume() {
        super.onResume()

        binding.viewModel?.setData("Test")
        binding.textResult.text = binding.viewModel?.getData()
    }

    private fun setSpinnerAdapter(){
        val spinnerAdapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, names)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSendData.adapter = spinnerAdapter


        for (i in 0 until names.size){
            customSpinnerItemList.add(CustomSpinnerModel(names[i],icons[i]))
        }
        val customAdapter : CustomAdapter = CustomAdapter(this@MainActivity, R.layout.custom_spinner_items, customSpinnerItemList)
        binding.spinnerCustomSendData.adapter = customAdapter
    }

    private fun setEventListeners(){
        binding.buttonSendData.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                binding.viewModel?.setData("Button Was Clicked")
                binding.textResult.text = binding.viewModel?.getData()
            }

        })

        binding.switchSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.setData("Switch is On")
                    binding.textResult.text = binding.viewModel?.getData()
                } else if (isChecked == false) {
                    binding.viewModel?.setData("Switch is Off")
                    binding.textResult.text = binding.viewModel?.getData()
                }
            }
        })

        binding.toggleButtonSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView : CompoundButton , isChecked : Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.setData("Toggle Button is On")
                    binding.textResult.text = binding.viewModel?.getData()
                }
                else if (isChecked == false) {
                    binding.viewModel?.setData("Toggle Button is Off")
                    binding.textResult.text = binding.viewModel?.getData()
                }
            }
        })

        binding.radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                when (findViewById<View>(checkedId).id) {
                    R.id.radioOn -> {
                        binding.viewModel?.setData("Radio Button is On of your selected Radio Group")
                        binding.textResult.text = binding.viewModel?.getData()
                    }
                    R.id.radioOff -> {
                        binding.viewModel?.setData("Radio Button is Off of your selected Radio Group")
                        binding.textResult.text = binding.viewModel?.getData()
                    }
                }
            }
        })

        binding.checkboxSendData.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked == true) {
                    binding.viewModel?.setData("Check Box is On")
                    binding.textResult.text = binding.viewModel?.getData()
                    binding.checkboxSendData.text = "On"
                }
                else if (isChecked == false) {
                    binding.viewModel?.setData("Check Box is Off")
                    binding.textResult.text = binding.viewModel?.getData()
                    binding.checkboxSendData.text = "Off"
                }
            }

        })

        binding.spinnerSendData.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                isSpinnerTouch = true
                return false
            }
        })

        binding.spinnerSendData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isSpinnerTouch) {
                    binding.viewModel?.setData("Spinner value selected is " + names[position])
                    binding.textResult.text = binding.viewModel?.getData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.viewModel?.setData("Spinner Nothing selected")
                binding.textResult.text = binding.viewModel?.getData()
            }
        }

        binding.spinnerCustomSendData.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                isCustomSpinnerTouch = true
                return false
            }

        })

        binding.spinnerCustomSendData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isCustomSpinnerTouch) {
                    binding.viewModel?.setData("Customize Spinner value selected is " + names[position])
                    binding.textResult.text = binding.viewModel?.getData()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.viewModel?.setData("Customize Spinner Nothing selected")
                binding.textResult.text = binding.viewModel?.getData()
            }
        }

        binding.ratingBarSendData.onRatingBarChangeListener = object :  RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                binding.viewModel?.setData("Rating Bar value is ${rating}")
                binding.textResult.text = binding.viewModel?.getData()
            }
        }

        binding.seekBarSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.viewModel?.setProgressData(progress)
                binding.progressBarResult.progress = binding.viewModel?.getProgressData()?:0
                binding.viewModel?.setData("Seek Bar Value selected is " + progress)
                binding.textResult.text = binding.viewModel?.getData()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.seekBarDiscreteSendData.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar : SeekBar) {

            }

            override fun onProgressChanged(seekBar : SeekBar , progress : Int , fromUser : Boolean) {
                binding.viewModel?.setProgressData(progress)
                binding.progressBarResult.progress = binding.viewModel?.getProgressData()?:0
                binding.viewModel?.setData("Customize Seek Bar Value selected is " + progress)
                binding.textResult.text = binding.viewModel?.getData()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.seekBarSendData.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                binding.seekBarSendData.thumb = resources.getDrawable(
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

        binding.seekBarDiscreteSendData.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                binding.seekBarDiscreteSendData.thumb = resources.getDrawable(
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

        binding.buttonSendData.setOnClickListener(null)
        binding.switchSendData.setOnCheckedChangeListener(null)
        binding.toggleButtonSendData.setOnCheckedChangeListener(null)
        binding.radioGroup.setOnCheckedChangeListener(null)
        binding.checkboxSendData.setOnCheckedChangeListener(null)
        binding.spinnerSendData.onItemSelectedListener = null
        binding.spinnerCustomSendData.onItemSelectedListener = null
        binding.ratingBarSendData.onRatingBarChangeListener = null
    }
}