package com.example.jetpackcomponentsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.example.jetpackcomponentsapp.databinding.MainBinder

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
            checkWorkRequest()
        } else if (view == binding.buttonChainWork) {
            binding.getViewModel()?.setChainingWorkers(
                binding.editTextName.getText().toString()
            )
            observeWorkRequests()
            clearInputs()
            checkWorkRequest()
        }
    }

    override fun onResume() {
        super.onResume()
        checkWorkRequest()
    }

    private fun checkWorkRequest() {
        //val workSpec = binding.getViewModel()?.getOneTimeWorkRequestWorkSpec("asd")
        val workInfo = binding.getViewModel()?.getOneTimeWorkRequestWorkInfo()
        //Log.d(TAG, "onResume() WorkSpec worker_class_name ${workSpec?.workerClassName}  calculate Next Run Time ${workSpec?.calculateNextRunTime()} ${MainViewModel.getDate(workSpec?.calculateNextRunTime())}")
        Log.d(TAG, "onResume() WorkInfo State ${workInfo?.state?.name} ordinal ${workInfo?.state?.ordinal} Run Attempt Count ${workInfo?.runAttemptCount} Progress ${workInfo?.progress} ${workInfo?.progress?.getInt(Constants.WORKER_INT_PROGRESS,0)} is Finished ${workInfo?.state?.isFinished()}")
        Log.d(TAG, "CustomWorker WorkInfo Output Data Name ${workInfo?.getOutputData()?.getString(Constants.WORKER_OUTPUT_NAME)}")
        Log.d(TAG, "CustomWorker WorkInfo Output Data Values ${workInfo?.getOutputData()?.getString(Constants.WORKER_OUTPUT_VALUE)}")
    }

    private fun observeWorkRequests() {
        binding.getViewModel()?.observeOneTimeWorkRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CustomWorker WorkInfo State ${workInfo.state.name} ordinal ${workInfo.state.ordinal} Run Attempt Count ${workInfo.runAttemptCount} Progress ${workInfo.progress} ${workInfo.progress.getInt(Constants.WORKER_INT_PROGRESS,0)}")
                binding.textResult.setText(workInfo.state.name)
                if (workInfo.state.isFinished) {
                    Log.d(TAG, "CustomWorker WorkInfo Output Data Name ${workInfo.outputData.getString(Constants.WORKER_OUTPUT_NAME)}")
                    Log.d(TAG, "CustomWorker WorkInfo Output Data Values ${workInfo.outputData.getString(Constants.WORKER_OUTPUT_VALUE)}")
                    Toast.makeText(this@MainActivity, workInfo.outputData.getString(Constants.WORKER_OUTPUT_NAME), Toast.LENGTH_LONG).show()
                }
            }
        })
        binding.getViewModel()?.observeFilteringWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "FilteringWorker WorkInfo State ${workInfo.state.name} ordinal ${workInfo.state.ordinal}")
                binding.textResult.setText(workInfo.state.name)
            }
        })
        binding.getViewModel()?.observeCompressingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CompressingWorker WorkInfo State ${workInfo.state.name} ordinal ${workInfo.state.ordinal}")
                binding.textResult.setText(workInfo.state.name)
            }
        })
        binding.getViewModel()?.observeUploadingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "UploadingWorker WorkInfo State ${workInfo.state.name} ordinal ${workInfo.state.ordinal}")
                binding.textResult.setText(workInfo.state.name)
            }
        })
        binding.getViewModel()?.observeDownloadingWorRequest()?.observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "DownloadingWorker WorkInfo State ${workInfo.state.name} ordinal ${workInfo.state.ordinal}")
                binding.textResult.setText(workInfo.state.name)
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