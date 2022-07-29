package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.delay

class MainViewModel : AndroidViewModel {

    private var customRepository : CustomRepository
    private var liveList : LiveData<List<CustomModel>>
    private var liveUpdate : MutableLiveData<CustomModel>
    private var liveStandBy : MutableLiveData<Boolean>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveList = MutableLiveData(emptyList<CustomModel>())
        liveUpdate = MutableLiveData(CustomModel())
        liveStandBy = MutableLiveData(null)
    }

    fun viewDidLoad() {
        liveStandBy.setValue(true)
    }

    fun checkIfFragmentLoaded(baseFragment: Fragment) { Log.d("MainViewModel","checkIfFragmentLoaded")
        Coroutines.default(this, {
            while (!baseFragment.isVisible) delay(100)
            viewWillAppear()
        })
    }

    fun viewWillAppear() { Log.d("MainViewModel","viewWillAppear")
        Coroutines.main(this@MainViewModel, {
            delay(500)
            liveStandBy.setValue(false)
        })
    }

    fun getLoadState() : LiveData<Boolean> {
        return liveStandBy
    }

    @Deprecated("For Static Data")
    fun setItems() {
        viewDidLoad()
        Coroutines.io {
            customRepository.deleteAll()
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 0")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 1")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 2")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 3")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 4")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 5")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 6")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 7")))
            customRepository.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground, "name 8")))
        }
        viewWillAppear()
    }

    fun setUpdate(item : CustomModel) {
        viewDidLoad()
        Coroutines.io(this@MainViewModel, {
            liveUpdate.postValue(item)
        } )
    }

    fun observeUpdate() : LiveData<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) {
        viewDidLoad()
        Coroutines.io(this@MainViewModel, {
            customRepository.insert(
                    ConvertList.toEntity(item)
            )
        } )
        viewWillAppear()
    }

    fun updateItem(updated : String) {
        viewDidLoad()
        Coroutines.io(this@MainViewModel, {
            liveUpdate.getValue()?.name = updated
            customRepository.update(
                ConvertList.toEntity(liveUpdate.value ?: CustomModel())
            )
        })
        viewWillAppear()
    }

    fun deleteItem(item : CustomModel) {
        viewDidLoad()
        Coroutines.io {
            customRepository.delete(
                    ConvertList.toEntity(item)
            )
        }
        viewWillAppear()
    }

    public fun deleteAll() {
        viewDidLoad()
        Coroutines.io(this@MainViewModel, {
            customRepository.deleteAll()
        })
        viewWillAppear()
    }

    public fun getItems() : LiveData<List<CustomModel>> {
        liveList = ConvertList.toLiveDataListModel(
                customRepository.getAll()
        )
        return liveList
    }

    override fun onCleared() {
        super.onCleared()
    }
}

/*
//https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Heavy operation that cannot be done in the Main Thread
     */
    fun launchDataLoad() {
        uiScope.launch {
            sortList()
            // Modify UI
        }
    }

    suspend fun sortList() = withContext(Dispatchers.Default) {
        // Heavy work
    }
 */