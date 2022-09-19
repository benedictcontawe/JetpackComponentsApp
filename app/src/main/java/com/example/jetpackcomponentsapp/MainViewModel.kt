package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.delay

public class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG = MainViewModel::class.java.getSimpleName()
    }

    private val customRepository : CustomRepository
    private var liveUpdate : MutableLiveData<CustomModel>
    private var liveStandBy : MutableLiveData<Boolean>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository(application)
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

    public fun observeLoadState() : LiveData<Boolean> {
        return liveStandBy
    }

    public fun setUpdate(item : CustomModel?) { Coroutines.io(this@MainViewModel, {
        if (item != null) {
            liveStandBy.postValue(true)
            liveUpdate.postValue(item)
        }
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
        val old : CustomModel? = liveUpdate.value
        customRepository.update(
            ConvertList.toEntity(CustomModel(old?.id, updated, old?.icon))
        )
        viewWillAppear()
    }) }

    public fun deleteItem(item : CustomModel?) { Coroutines.io(this@MainViewModel, {
        liveStandBy.postValue(true)
        if (item != null)
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

    public fun observeItems() : LiveData<List<CustomModel>> {
        return ConvertList.toLiveDataListModel(
            customRepository.getAll() ?: MutableLiveData<List<CustomEntity>>(listOf<CustomEntity>())
        )
    }

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            customRepository.onCLose()
        })
        super.onCleared()
    }
}