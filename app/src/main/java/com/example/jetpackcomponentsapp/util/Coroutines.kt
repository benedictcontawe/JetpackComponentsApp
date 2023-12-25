package com.example.jetpackcomponentsapp.util

import androidx.lifecycle.*
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
| - Updating Flow                   |
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
    fun main(viewModel : ViewModel, work : suspend (() -> Unit)) {
        viewModel.viewModelScope.launch(Dispatchers.Main) {
            work()
        }
    }
    /*
    fun main(activity : AppCompatActivity, work : suspend ((scope : CoroutineScope) -> Unit)) =
        activity.lifecycleScope.launch {
            activity.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                work(this)
            }
        }
    fun main(fragment : BottomSheetDialogFragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    fun main(fragment : DialogFragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    fun main(fragment : Fragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    */
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
    fun default(work : suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Default).launch {
            work()
        }

    fun default(viewModel : ViewModel, work : suspend (() -> Unit)) =
        viewModel.viewModelScope.launch(Dispatchers.Default) {
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