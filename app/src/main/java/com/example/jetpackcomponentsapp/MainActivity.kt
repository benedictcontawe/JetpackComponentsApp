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
        binding.button.setOnClickListener(this@MainActivity)
    }

    override fun onClick(view : View) {
        if (view == binding.button) {
            setOneTimeWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() { Log.d(TAG,"setOneTimeWorkRequest()")
        val WORK_NAME = "SingleBackupWorker"
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
        val oneTimeWorkRequest : OneTimeWorkRequest =
                OneTimeWorkRequest.Builder(CustomWorker::class.java)
                        .setConstraints(constraints)
                        .setInputData(data)
                        .setInitialDelay(binding.getViewModel()!!.getScheduleWork(0,1),TimeUnit.MICROSECONDS)
                        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                        .build()
        workManager.enqueue(oneTimeWorkRequest)
        //workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this@MainActivity, object : Observer<WorkInfo> {
            override fun onChanged(workInfo : WorkInfo) {
                Log.d(TAG, "WorkInfo State ${workInfo.getState().name} ordinal ${workInfo.getState().ordinal}")
                binding.textResult.setText(workInfo.getState().name)
                if (workInfo.getState().isFinished()) {
                    Log.d(TAG, "WorkInfo Output Data Name ${workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_NAME)}")
                    Log.d(TAG, "WorkInfo Output Data Values ${workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_VALUE)}")
                    Toast.makeText(this@MainActivity,
                            workInfo.getOutputData().getString(Constants.WORKER_OUTPUT_NAME),
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        clearInputs()
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