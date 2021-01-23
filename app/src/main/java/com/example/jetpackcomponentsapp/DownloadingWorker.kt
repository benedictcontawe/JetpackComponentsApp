package com.example.jetpackcomponentsapp

import android.content.Context
import android.util.Log
import androidx.work.*

class DownloadingWorker : Worker {

    companion object {
        private val TAG : String = UploadingWorker::class.java.getSimpleName()
    }

    constructor(context : Context, params : WorkerParameters) : super(context, params) {

    }

    override fun doWork() : ListenableWorker.Result {
        return try {
            if (getRunAttemptCount() > 3) ListenableWorker.Result.failure()
            else {
                for (index : Int in 0..300) {
                    Log.d(TAG,"Downloading $index")
                    //setForegroundAsync()
                    setProgressAsync(
                            Data.Builder()
                                    .putInt(Constants.WORKER_INT_PROGRESS, index)
                                    .build()
                    )
                }
                ListenableWorker.Result.success()
            }
        } catch (ex : Exception) {
            Log.e(TAG,"DownloadingWorker doWork() Error ${ex.message}")
            ListenableWorker.Result.retry()
        }
    }
}