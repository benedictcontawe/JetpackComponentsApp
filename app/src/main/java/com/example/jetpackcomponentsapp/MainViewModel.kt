package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.delay

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
    }

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository(application)
    }

    private val customRepository : CustomRepository
    private val liveUpdate : MutableLiveData<CustomModel?> = MutableLiveData<CustomModel?>(null)
    private val liveStandBy : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    public suspend fun viewDidLoad() {
        liveStandBy.postValue(true)
    }

    public fun checkIfFragmentLoaded(fragment : Fragment) { Coroutines.default(this@MainViewModel, {
        Log.d(TAG,"checkIfFragmentLoaded")
        while (!fragment.isVisible()) delay(100)
        viewWillAppear()
    }) }

    public suspend fun viewWillAppear() {
        Log.d(TAG,"viewWillAppear")
        delay(500)
        liveStandBy.postValue(false)
    }

    public fun observeLoadState() : LiveData<Boolean> {
        return liveStandBy
    }

    public fun setUpdate(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        Log.d(TAG, "setUpdate $model")
        viewDidLoad()
        liveUpdate.postValue(model)
        Log.d(TAG, "liveUpdate $liveUpdate")
    } ) }

    public fun observeUpdate() : LiveData<CustomModel?> {
        return liveUpdate
    }

    public fun insertItem(model : CustomModel) { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        customRepository.insert (
            ConvertList.toEntity(model)
        )
        viewWillAppear()
    } ) }

    public fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        val old : CustomModel? = liveUpdate.getValue()
        customRepository.update (
            ConvertList.toEntity(CustomModel(old?.id, updated, old?.icon))
        )
        viewWillAppear()
    }) }

    public fun deleteItem(model : CustomModel?) { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        if (model != null)
            customRepository.delete(
                ConvertList.toEntity(model)
            )
        viewWillAppear()
    } ) }

    public fun deleteAll() { Coroutines.io(this@MainViewModel, {
        viewDidLoad()
        customRepository.deleteAll()
        viewWillAppear()
    }) }

    public fun observeItems() : LiveData<List<CustomModel>> {
        return ConvertList.toLiveDataListModel (
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