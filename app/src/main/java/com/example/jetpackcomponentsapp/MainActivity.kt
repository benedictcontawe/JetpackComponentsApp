package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.jetpackcomponentsapp.databinding.MainBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : MainBinder

    companion object {
        private val TAG : String = MainActivity::class.java.getSimpleName()

    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main) //setContentView(R.layout.activity_main)
        binding.setViewModel(ViewModelProvider(this@MainActivity).get(MainViewModel::class.java))
        binding.setLifecycleOwner(this@MainActivity)
        binding.buttonEnqueueWork.setOnClickListener(this@MainActivity)
        binding.buttonChainWork.setOnClickListener(this@MainActivity)
        observeWorkRequests()
    }

    override fun onClick(view : View) {
        if (view == binding.buttonEnqueueWork) {
            binding.getViewModel()?.setOneTimeWorkRequest(
                    binding.editTextName.getText().toString(),
                    binding.editTextInt.getText().toString(),
                    binding.editTextString.getText().toString(),
                    binding.editTextLong.getText().toString()
            )
            observeWorkRequests()
            clearInputs()
        } else if (view == binding.buttonChainWork) {
            binding.getViewModel()?.setChainingWorkers(
                    binding.editTextName.getText().toString()
            )
            observeWorkRequests()
            clearInputs()
        }
    }

    private fun observeWorkRequests() {
        binding.getViewModel()?.observeOneTimeWorkRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CustomWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal} Run Attempt Count ${workInfo.getRunAttemptCount()} Progress ${workInfo.getProgress()} ${workInfo.getProgress().getInt(Constants.WORKER_PROGRESS,0)}")
                binding.textResult.setText(workInfo.getState().name)
                if (workInfo.getState().isFinished()) {
                    Log.d(TAG, "CustomWorker WorkInfo Output Data Name ${workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_NAME)}")
                    Log.d(TAG, "CustomWorker WorkInfo Output Data Values ${workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_VALUE)}")
                    Toast.makeText(this@MainActivity,
                            workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_NAME),
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        binding.getViewModel()?.observeFilteringWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "FilteringWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        binding.getViewModel()?.observeCompressingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CompressingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        binding.getViewModel()?.observeUploadingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "UploadingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        binding.getViewModel()?.observeDownloadingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "DownloadingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
    }

    private fun clearInputs() {
        binding.editTextName.getText()?.clear()
        binding.editTextInt.getText()?.clear()
        binding.editTextString.getText()?.clear()
        binding.editTextLong.getText()?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}