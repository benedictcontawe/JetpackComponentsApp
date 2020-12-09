package com.example.jetpackcomponentsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    companion object {
        public fun getDate() : String {
            return SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(Date())
        }
    }

    private var data : MutableLiveData<String> = MutableLiveData()
    private var progressData : MutableLiveData<Int> = MutableLiveData()

    public fun getData() : LiveData<String> {
        return data
    }

    public fun setData(data: String) {
        this.data.setValue(data)
    }

    public fun getProgressData() : LiveData<Int> {
        return progressData
    }

    public fun setProgressData(progressData: Int){
        this.progressData.setValue(progressData)
    }

    public fun getScheduleWork(hour: Int, minute: Int) : Long {
        val calendar : Calendar = Calendar.getInstance()
        val nowMillis : Long = calendar.getTimeInMillis()

        if(calendar.get(Calendar.HOUR_OF_DAY) > hour || (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE)+1 >= minute)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis() - nowMillis;
    }

    public fun getInteger(text : String) : Int {
        val re = Regex("\\D+") //^-?\d+(,\d+)*(\.\d+(e\d+)?)?$
        return re.replace(text, "").toInt()
    }

    public fun getLong(text : String) : Long {
        val re = Regex("\\D+") //^-?\d+(,\d+)*(\.\d+(e\d+)?)?$
        return re.replace(text, "").toLong()
    }
}