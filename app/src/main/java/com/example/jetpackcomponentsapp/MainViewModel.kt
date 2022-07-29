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
    private var liveUpdate : MutableLiveData<CustomModel>
    private var liveStandBy : MutableLiveData<Boolean>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveUpdate = MutableLiveData(CustomModel())
        liveStandBy = MutableLiveData(null)
    }

    public fun viewDidLoad() {
        liveStandBy.setValue(true)
    }

    public fun checkIfFragmentLoaded(fragment : Fragment) { Log.d("MainViewModel","checkIfFragmentLoaded")
        Coroutines.default(this@MainViewModel, {
            while (!fragment.isVisible()) delay(100)
            viewWillAppear()
        })
    }

    public suspend fun viewWillAppear() { Log.d("MainViewModel","viewWillAppear")
        delay(500)
        liveStandBy.postValue(false)
    }

    public fun getLoadState() : LiveData<Boolean> {
        return liveStandBy
    }

    @Deprecated("For Static Data")
    public fun setItems() { Coroutines.io (this@MainViewModel, {
        liveStandBy.postValue(true)
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
        viewWillAppear()
    } ) }

    public fun setUpdate(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        liveUpdate.postValue(item)
    } ) }

    public fun observeUpdate() : LiveData<CustomModel> {
        return liveUpdate
    }

    public fun insertItem(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        customRepository.insert(
                ConvertList.toEntity(item)
        )
        viewWillAppear()
    } ) }

    public fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        liveUpdate.getValue()?.name = updated
        customRepository.update(
            ConvertList.toEntity(liveUpdate.value ?: CustomModel())
        )
        viewWillAppear()
    }) }

    public fun deleteItem(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        customRepository.delete(
                ConvertList.toEntity(item)
        )
        viewWillAppear()
    } ) }

    public fun deleteAll() { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        customRepository.deleteAll()
        viewWillAppear()
    }) }

    public fun getItems() : LiveData<List<CustomModel>> {
        return ConvertList.toLiveDataListModel(
            customRepository.getAll()
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}