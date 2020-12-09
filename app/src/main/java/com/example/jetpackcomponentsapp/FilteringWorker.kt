package com.example.jetpackcomponentsapp

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class FilteringWorker : Worker {

    companion object {
        private val TAG : String = FilteringWorker::class.java.getSimpleName()
    }

    constructor(context : Context, params : WorkerParameters) : super(context, params) {

    }

    override fun doWork() : Result {
        return try {
            if (getRunAttemptCount() > 3) Result.failure()
            else {
                for (index : Int in 0..300) {
                    Log.d(TAG,"Filtering $index")
                }
                Result.success()
                            }
        } catch (ex : Exception) {
            Log.e(TAG,"FilteringWorker doWork() Error ${ex.message}")
            Result.retry()
        }
    }
}