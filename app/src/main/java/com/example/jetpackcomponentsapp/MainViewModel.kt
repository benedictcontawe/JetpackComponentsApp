package com.example.jetpackcomponentsapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomponentsapp.model.CustomModel
import com.example.jetpackcomponentsapp.util.ConvertList
import com.example.jetpackcomponentsapp.repository.CustomRepository
import com.example.jetpackcomponentsapp.room.CustomEntity
import com.example.jetpackcomponentsapp.util.Coroutines
import kotlinx.coroutines.flow.*

class MainViewModel : AndroidViewModel {

    private val customRepository : CustomRepository
    private val liveUpdate : MutableStateFlow<CustomModel>

    constructor(application: Application) : super(application) {
        customRepository = CustomRepository.getInstance(application)
        liveUpdate = MutableStateFlow(CustomModel())
    }

    @Deprecated("For Static Data")
    fun setItems() { Coroutines.default(this@MainViewModel, {
        customRepository.deleteAll()
        for (index in 0 until 500) {
            customRepository.insert(ConvertList.toEntity(CustomModel(index, "name $index")))
        }
        /*
        customRepository.insert(ConvertList.toEntity(CustomModel(0,"name 0")))
        customRepository.insert(ConvertList.toEntity(CustomModel(1,"name 1")))
        customRepository.insert(ConvertList.toEntity(CustomModel(2,"name 2")))
        customRepository.insert(ConvertList.toEntity(CustomModel(3,"name 3")))
        customRepository.insert(ConvertList.toEntity(CustomModel(4,"name 4")))
        customRepository.insert(ConvertList.toEntity(CustomModel(5,"name 5")))
        customRepository.insert(ConvertList.toEntity(CustomModel(6,"name 6")))
        customRepository.insert(ConvertList.toEntity(CustomModel(7,"name 7")))
        customRepository.insert(ConvertList.toEntity(CustomModel(8,"name 8")))
        customRepository.insert(ConvertList.toEntity(CustomModel(9,"name 9")))

        customRepository.insert(ConvertList.toEntity(CustomModel(10,"name 10")))
        customRepository.insert(ConvertList.toEntity(CustomModel(11,"name 11")))
        customRepository.insert(ConvertList.toEntity(CustomModel(12,"name 12")))
        customRepository.insert(ConvertList.toEntity(CustomModel(13,"name 13")))
        customRepository.insert(ConvertList.toEntity(CustomModel(14,"name 14")))
        customRepository.insert(ConvertList.toEntity(CustomModel(15,"name 15")))
        customRepository.insert(ConvertList.toEntity(CustomModel(16,"name 16")))
        customRepository.insert(ConvertList.toEntity(CustomModel(17,"name 17")))
        customRepository.insert(ConvertList.toEntity(CustomModel(18,"name 18")))
        customRepository.insert(ConvertList.toEntity(CustomModel(19,"name 19")))

        customRepository.insert(ConvertList.toEntity(CustomModel(20,"name 20")))
        customRepository.insert(ConvertList.toEntity(CustomModel(21,"name 21")))
        customRepository.insert(ConvertList.toEntity(CustomModel(22,"name 22")))
        customRepository.insert(ConvertList.toEntity(CustomModel(23,"name 23")))
        customRepository.insert(ConvertList.toEntity(CustomModel(24,"name 24")))
        customRepository.insert(ConvertList.toEntity(CustomModel(25,"name 25")))
        customRepository.insert(ConvertList.toEntity(CustomModel(26,"name 26")))
        customRepository.insert(ConvertList.toEntity(CustomModel(27,"name 27")))
        customRepository.insert(ConvertList.toEntity(CustomModel(28,"name 28")))
        customRepository.insert(ConvertList.toEntity(CustomModel(29,"name 29")))

        customRepository.insert(ConvertList.toEntity(CustomModel(30,"name 30")))
        customRepository.insert(ConvertList.toEntity(CustomModel(31,"name 31")))
        customRepository.insert(ConvertList.toEntity(CustomModel(32,"name 32")))
        customRepository.insert(ConvertList.toEntity(CustomModel(33,"name 33")))
        customRepository.insert(ConvertList.toEntity(CustomModel(34,"name 34")))
        customRepository.insert(ConvertList.toEntity(CustomModel(35,"name 35")))
        customRepository.insert(ConvertList.toEntity(CustomModel(36,"name 36")))
        customRepository.insert(ConvertList.toEntity(CustomModel(37,"name 37")))
        customRepository.insert(ConvertList.toEntity(CustomModel(38,"name 38")))
        customRepository.insert(ConvertList.toEntity(CustomModel(39,"name 39")))

        customRepository.insert(ConvertList.toEntity(CustomModel(40,"name 40")))
        customRepository.insert(ConvertList.toEntity(CustomModel(41,"name 41")))
        customRepository.insert(ConvertList.toEntity(CustomModel(42,"name 42")))
        customRepository.insert(ConvertList.toEntity(CustomModel(43,"name 43")))
        customRepository.insert(ConvertList.toEntity(CustomModel(44,"name 44")))
        customRepository.insert(ConvertList.toEntity(CustomModel(45,"name 45")))
        customRepository.insert(ConvertList.toEntity(CustomModel(46,"name 46")))
        customRepository.insert(ConvertList.toEntity(CustomModel(47,"name 47")))
        customRepository.insert(ConvertList.toEntity(CustomModel(48,"name 48")))
        customRepository.insert(ConvertList.toEntity(CustomModel(49,"name 49")))

        customRepository.insert(ConvertList.toEntity(CustomModel(50,"name 50")))
        customRepository.insert(ConvertList.toEntity(CustomModel(51,"name 51")))
        customRepository.insert(ConvertList.toEntity(CustomModel(52,"name 52")))
        customRepository.insert(ConvertList.toEntity(CustomModel(53,"name 53")))
        customRepository.insert(ConvertList.toEntity(CustomModel(54,"name 54")))
        customRepository.insert(ConvertList.toEntity(CustomModel(55,"name 55")))
        customRepository.insert(ConvertList.toEntity(CustomModel(56,"name 56")))
        customRepository.insert(ConvertList.toEntity(CustomModel(57,"name 57")))
        customRepository.insert(ConvertList.toEntity(CustomModel(58,"name 58")))
        customRepository.insert(ConvertList.toEntity(CustomModel(59,"name 59")))

        customRepository.insert(ConvertList.toEntity(CustomModel(60,"name 60")))
        customRepository.insert(ConvertList.toEntity(CustomModel(61,"name 61")))
        customRepository.insert(ConvertList.toEntity(CustomModel(62,"name 62")))
        customRepository.insert(ConvertList.toEntity(CustomModel(63,"name 63")))
        customRepository.insert(ConvertList.toEntity(CustomModel(64,"name 64")))
        customRepository.insert(ConvertList.toEntity(CustomModel(65,"name 65")))
        customRepository.insert(ConvertList.toEntity(CustomModel(66,"name 66")))
        customRepository.insert(ConvertList.toEntity(CustomModel(67,"name 67")))
        customRepository.insert(ConvertList.toEntity(CustomModel(68,"name 68")))
        customRepository.insert(ConvertList.toEntity(CustomModel(69,"name 69")))

        customRepository.insert(ConvertList.toEntity(CustomModel(70,"name 70")))
        customRepository.insert(ConvertList.toEntity(CustomModel(71,"name 71")))
        customRepository.insert(ConvertList.toEntity(CustomModel(72,"name 72")))
        customRepository.insert(ConvertList.toEntity(CustomModel(73,"name 73")))
        customRepository.insert(ConvertList.toEntity(CustomModel(74,"name 74")))
        customRepository.insert(ConvertList.toEntity(CustomModel(75,"name 75")))
        customRepository.insert(ConvertList.toEntity(CustomModel(76,"name 76")))
        customRepository.insert(ConvertList.toEntity(CustomModel(77,"name 77")))
        customRepository.insert(ConvertList.toEntity(CustomModel(78,"name 78")))
        customRepository.insert(ConvertList.toEntity(CustomModel(79,"name 79")))

        customRepository.insert(ConvertList.toEntity(CustomModel(80,"name 80")))
        customRepository.insert(ConvertList.toEntity(CustomModel(81,"name 81")))
        customRepository.insert(ConvertList.toEntity(CustomModel(82,"name 82")))
        customRepository.insert(ConvertList.toEntity(CustomModel(83,"name 83")))
        customRepository.insert(ConvertList.toEntity(CustomModel(84,"name 84")))
        customRepository.insert(ConvertList.toEntity(CustomModel(85,"name 85")))
        customRepository.insert(ConvertList.toEntity(CustomModel(86,"name 86")))
        customRepository.insert(ConvertList.toEntity(CustomModel(87,"name 87")))
        customRepository.insert(ConvertList.toEntity(CustomModel(88,"name 88")))
        customRepository.insert(ConvertList.toEntity(CustomModel(89,"name 89")))

        customRepository.insert(ConvertList.toEntity(CustomModel(90,"name 90")))
        customRepository.insert(ConvertList.toEntity(CustomModel(91,"name 91")))
        customRepository.insert(ConvertList.toEntity(CustomModel(92,"name 92")))
        customRepository.insert(ConvertList.toEntity(CustomModel(93,"name 93")))
        customRepository.insert(ConvertList.toEntity(CustomModel(94,"name 94")))
        customRepository.insert(ConvertList.toEntity(CustomModel(95,"name 95")))
        customRepository.insert(ConvertList.toEntity(CustomModel(96,"name 96")))
        customRepository.insert(ConvertList.toEntity(CustomModel(97,"name 97")))
        customRepository.insert(ConvertList.toEntity(CustomModel(98,"name 98")))
        customRepository.insert(ConvertList.toEntity(CustomModel(99,"name 99")))

        customRepository.insert(ConvertList.toEntity(CustomModel(100,"name 100")))
        customRepository.insert(ConvertList.toEntity(CustomModel(101,"name 101")))
        customRepository.insert(ConvertList.toEntity(CustomModel(102,"name 102")))
        customRepository.insert(ConvertList.toEntity(CustomModel(103,"name 103")))
        customRepository.insert(ConvertList.toEntity(CustomModel(104,"name 104")))
        customRepository.insert(ConvertList.toEntity(CustomModel(105,"name 105")))
        customRepository.insert(ConvertList.toEntity(CustomModel(106,"name 106")))
        customRepository.insert(ConvertList.toEntity(CustomModel(107,"name 107")))
        customRepository.insert(ConvertList.toEntity(CustomModel(108,"name 108")))
        customRepository.insert(ConvertList.toEntity(CustomModel(109,"name 109")))

        customRepository.insert(ConvertList.toEntity(CustomModel(110,"name 110")))
        customRepository.insert(ConvertList.toEntity(CustomModel(111,"name 111")))
        customRepository.insert(ConvertList.toEntity(CustomModel(112,"name 112")))
        customRepository.insert(ConvertList.toEntity(CustomModel(113,"name 113")))
        customRepository.insert(ConvertList.toEntity(CustomModel(114,"name 114")))
        customRepository.insert(ConvertList.toEntity(CustomModel(115,"name 115")))
        customRepository.insert(ConvertList.toEntity(CustomModel(116,"name 116")))
        customRepository.insert(ConvertList.toEntity(CustomModel(117,"name 117")))
        customRepository.insert(ConvertList.toEntity(CustomModel(118,"name 118")))
        customRepository.insert(ConvertList.toEntity(CustomModel(119,"name 119")))
        */
    }) }

    fun setUpdate(item : CustomModel) { Coroutines.io(this@MainViewModel) {
        liveUpdate.emit(item)
    } }

    fun observeUpdate() : StateFlow<CustomModel> {
        return liveUpdate
    }

    fun insertItem(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.insert(
            ConvertList.toEntity(item)
        )
    }) }

    fun updateItem(updated : String) { Coroutines.io(this@MainViewModel, {
        liveUpdate.value.name = updated
        customRepository.update(
            ConvertList.toEntity(liveUpdate.value)
        )
    }) }

    fun deleteItem(item : CustomModel) { Coroutines.io(this@MainViewModel, {
        customRepository.delete(
            ConvertList.toEntity(item)
        )
    }) }

    fun deleteAll() { Coroutines.io(this@MainViewModel, {
        customRepository.deleteAll()
    }) }

    suspend fun observeItems(): StateFlow<List<CustomModel>> {
        return ConvertList.toStateFlowListModel(customRepository.getAll() ?: emptyFlow<List<CustomEntity>>(), viewModelScope)
    }

    override fun onCleared() {
        Coroutines.io(this@MainViewModel, {
            customRepository.onCLose()
        })
        super.onCleared()
    }
}