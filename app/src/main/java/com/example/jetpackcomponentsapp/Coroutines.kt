package com.example.jetpackcomponentsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * https://medium.com/androiddevelopers/coroutines-on-android-part-i-getting-the-background-3e0e54d20bb
+-----------------------------------+
|         Dispatchers.Main          |
+-----------------------------------+
| Main thread on Android, interact  |
| with the UI and perform light     |
| work                              |
+-----------------------------------+
| - Calling suspend functions       |
| - Call UI functions               |
| - Updating LiveData               |
+-----------------------------------+
+-----------------------------------+
|          Dispatchers.IO           |
+-----------------------------------+
| Optimized for disk and network IO |
| off the main thread               |
+-----------------------------------+
| - Database*                       |
| - Reading/writing files           |
| - Networking**                    |
+-----------------------------------+
+-----------------------------------+
|        Dispatchers.Default        |
+-----------------------------------+
| Optimized for CPU intensive work  |
| off the main thread               |
+-----------------------------------+
| - Sorting a list                  |
| - Parsing JSON                    |
| - DiffUtils                       |
+-----------------------------------+
 */

object Coroutines {
    //region UI contexts
    fun main(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Main.immediate).launch {
                work()
            }
    //endregion
    //region I/O operations
    fun io(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.IO).launch {
                work()
            }

    fun io(viewModel : ViewModel, work : suspend (() -> Unit)) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            work()
        }
    }
    //endregion
    //region Uses heavy CPU computation
    fun defaultGlobal(work : suspend (() -> Unit)) {
        GlobalScope.async(Dispatchers.Default) {
            work()
        }
    }

    fun default(viewModel : ViewModel, work : suspend (() -> Unit)) {
        viewModel.viewModelScope.launch(Dispatchers.Default) {
            work()
        }
    }

    fun default(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Default).launch {
                work()
            }
    //endregion
    //region No need to run on specific context
    fun unconfined(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Unconfined).launch {
                work()
            }
    //endregion
}