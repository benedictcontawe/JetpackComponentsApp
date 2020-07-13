package com.example.jetpackcomponentsapp.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

// UiThreadExecutor implementation example
class UiThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        handler.post(command)
    }
}