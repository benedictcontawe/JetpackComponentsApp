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
import java.util.concurrent.TimeUnit

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
    }

    override fun onClick(view : View) {
        if (view == binding.buttonEnqueueWork) {
            setOneTimeWorkRequest()
        } else if (view == binding.buttonChainWork) {
            setChainingWorkers()
        }
    }

    private fun setOneTimeWorkRequest() { Log.d(TAG,"setOneTimeWorkRequest()")
        val workManager : WorkManager = WorkManager.getInstance(getApplicationContext())
        val data : Data = Data.Builder()
                .putString(Constants.WORKER_NAME,
                        binding.editTextName.getText().toString()
                )
                .putInt(Constants.WORKER_INT,
                        binding.getViewModel()!!.getInteger(
                                binding.editTextInt.getText().toString()
                        )
                )
                .putString(Constants.WORKER_STRING,
                        binding.editTextString.getText().toString()
                )
                .putLong(Constants.WORKER_LONG,
                        binding.getViewModel()!!.getLong(
                                binding.editTextLong.getText().toString()
                        )
                ).build()
        val constraints : Constraints =
                Constraints.Builder()
                        .setRequiresCharging(false)
                        .setRequiresBatteryNotLow(false)
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
        workManager.cancelAllWorkByTag(binding.editTextName.getText().toString())
        val oneTimeWorkRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(CustomWorker::class.java)
                        .setInitialDelay(binding.getViewModel()!!.getScheduleWork(0,1),TimeUnit.MICROSECONDS)
                        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .setInputData(data)
                        .addTag(binding.editTextName.getText().toString())
                        .build()
        workManager.enqueue(oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CustomWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
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
        clearInputs()
    }

    private fun setChainingWorkers() { Log.d(TAG,"setChainingWorkers()")
        Log.d(TAG,"Chaining Workers Filter -> Compress -> Upload")
        val WORK_NAME = "SingleBackupWorker"
        val workManager : WorkManager = WorkManager.getInstance(getApplicationContext())
        val constraints : Constraints =
                Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
        workManager.cancelAllWorkByTag(binding.editTextName.getText().toString())
        val filteringWorRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(FilteringWorker::class.java)
                        .build()
        val compressingWorkRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(CompressingWorker::class.java)
                        .build()
        val uploadingWorRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(UploadingWorker::class.java)
                        .build()
        val downloadingWorRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
                        .build()
        val parallelWorks : MutableList<OneTimeWorkRequest> = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(downloadingWorRequest)
        parallelWorks.add(filteringWorRequest)

        //workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
        workManager
                .beginWith(parallelWorks)
                //.beginWith(filteringWorRequest)
                .then(compressingWorkRequest)
                .then(uploadingWorRequest)
                .enqueue()
        workManager.getWorkInfoByIdLiveData(filteringWorRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "FilteringWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        workManager.getWorkInfoByIdLiveData(compressingWorkRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "CompressingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        workManager.getWorkInfoByIdLiveData(uploadingWorRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "UploadingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
        workManager.getWorkInfoByIdLiveData(downloadingWorRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "DownloadingWorker WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
            }
        })
    }

    private fun setPeriodWorkRequest() { Log.d(TAG, "setPeriodWorkRequest()")
        val periodicWorkRequest : PeriodicWorkRequest = PeriodicWorkRequest
                .Builder(DownloadingWorker::class.java, 15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest)
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