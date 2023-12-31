package com.example.jetpackcomponentsapp.util

//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.Fragment
import androidx.lifecycle.*
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
public object Coroutines {
    //region UI contexts
    public fun main(work : suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main.immediate).launch {
            work()
        }
    /*
    public fun main(activity : AppCompatActivity, work : suspend ((scope : CoroutineScope) -> Unit)) =
        activity.lifecycleScope.launch {
            activity.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                work(this)
            }
        }
    public fun main(fragment : BottomSheetDialogFragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    public fun main(fragment : DialogFragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    public fun main(fragment : Fragment, work : suspend ((scope : CoroutineScope) -> Unit)) =
        fragment.lifecycleScope.launch {
            fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                work(this)
            }
        }
    */
    //endregion
    //region I/O operations
    public fun io(work : suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    public fun io(viewModel : ViewModel, work : suspend (() -> Unit)) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            work()
        }
    }
    //endregion
    //region Uses heavy CPU computation
    public fun default(work : suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Default).launch {
            work()
        }

    public fun default(viewModel : ViewModel, work : suspend (() -> Unit)) =
        viewModel.viewModelScope.launch(Dispatchers.Default) {
            work()
        }
    //endregion
    //region No need to run on specific context
    public fun unconfined(work : suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Unconfined).launch {
            work()
        }
    //endregion
}