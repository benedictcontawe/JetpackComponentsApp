package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines

class MainViewModel : AndroidViewModel {

    private lateinit var customRepository : CustomRepository
    private lateinit var liveList : LiveData<MutableList<CustomModel>>
    private lateinit var liveUpdate : MutableLiveData<CustomModel>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveList = MutableLiveData()
        liveUpdate = MutableLiveData()
    }

    @Deprecated("For Static Data")
    fun setItems() {
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
    }

    fun setUpdate(item : CustomModel) {
        Coroutines.main {
            liveUpdate.value = item
        }
    }

    fun getUpdate() : LiveData<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) {
        Coroutines.io {
            customRepository.insert(
                    ConvertList.toEntity(item)
            )
        }
    }

    fun updateItem() {
        liveUpdate.value?.let {
            Coroutines.io {
                customRepository.update(
                        ConvertList.toEntity(it)
                )
            }
        }
    }

    fun deleteItem(item : CustomModel) {
        Coroutines.io {
            customRepository.delete(
                    ConvertList.toEntity(item)
            )
        }
    }

    fun deleteAll() {
        Coroutines.io {
            customRepository.deleteAll()
        }
    }

    fun getItems() : LiveData<MutableList<CustomModel>> {
        liveList = ConvertList.toLiveDataListModel(
                customRepository.getAll()
        )
        return liveList
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