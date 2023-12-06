package com.example.jetpackcompose

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class AppDelegate : MultiDexApplication() {

    override fun attachBaseContext(base : Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}