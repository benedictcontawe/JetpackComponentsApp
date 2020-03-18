package com.example.jetpackcomponentsapp.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    //UI contexts
    fun main(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Main).launch {
                work()
            }
    // I/O operations
    fun io(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.IO).launch {
                work()
            }
    // Uses heavy CPU computation
    fun default(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Default).launch {
                work()
            }
    // No need to run on specific context
    fun unconfined(work : suspend (() -> Unit)) =
            CoroutineScope(Dispatchers.Unconfined).launch {
                work()
            }
}