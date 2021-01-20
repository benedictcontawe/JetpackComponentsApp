package com.example.jetpackcomponentsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel : AndroidViewModel {

    companion object {
        private val TAG : String = MainViewModel::class.java.getSimpleName()
        public fun getDate() : String {
            return SimpleDateFormat("M/dd/yyyy hh:mm:ss").format(Date())
        }
    }

    private val workManager : WorkManager
    private val constraints : Constraints
    private var oneTimeWorkRequest : OneTimeWorkRequest? = null
    private var filteringWorRequest : OneTimeWorkRequest? = null
    private var compressingWorkRequest : OneTimeWorkRequest? = null
    private var uploadingWorRequest : OneTimeWorkRequest? = null
    private var downloadingWorRequest : OneTimeWorkRequest? = null
    private lateinit var parallelWorks : MutableList<OneTimeWorkRequest>

    private var data : MutableLiveData<String> = MutableLiveData()
    private var progressData : MutableLiveData<Int> = MutableLiveData()

    constructor(application : Application) : super(application) {

    }

    init {
        workManager = WorkManager.getInstance(getApplication())
        constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setRequiresBatteryNotLow(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
    }

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

    private fun getScheduleWork(hour: Int, minute: Int) : Long {
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

    private fun getInteger(text : String) : Int {
        val re = Regex("\\D+") //^-?\d+(,\d+)*(\.\d+(e\d+)?)?$
        return re.replace(text, "").toIntOrNull()?:0
    }

    private fun getLong(text : String) : Long {
        val re = Regex("\\D+") //^-?\d+(,\d+)*(\.\d+(e\d+)?)?$
        return re.replace(text, "").toLongOrNull()?:0L
    }

    public fun setOneTimeWorkRequest(name : String, int : String, string : String, long : String) {
        Log.d(TAG,"setOneTimeWorkRequest($name, $int, $string, $long)")
        val data : Data = Data.Builder()
                .putString(Constants.WORKER_NAME,
                        name
                )
                .putInt(Constants.WORKER_INT,
                        getInteger(int)
                )
                .putString(Constants.WORKER_STRING,
                        string
                )
                .putLong(Constants.WORKER_LONG,
                        getLong(long)
                ).build()
        workManager.cancelAllWorkByTag(name)
        oneTimeWorkRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setInitialDelay(getScheduleWork(0,1), TimeUnit.MICROSECONDS)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(name)
                .build()
        workManager.enqueue(oneTimeWorkRequest!!)
    }

    public fun setChainingWorkers(name : String) { Log.d(TAG,"setChainingWorkers($name)")
        Log.d(TAG,"Chaining Workers Filter -> Compress -> Upload")
        val WORK_NAME = "SingleBackupWorker"
        workManager.cancelAllWorkByTag(name)
        filteringWorRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java)
                .build()
        compressingWorkRequest = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
                .build()
        uploadingWorRequest = OneTimeWorkRequest.Builder(UploadingWorker::class.java)
                .build()
        downloadingWorRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
                .build()
        parallelWorks = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(downloadingWorRequest!!)
        parallelWorks.add(filteringWorRequest!!)

        //workManager.enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
        workManager
                .beginWith(parallelWorks)
                //.beginWith(filteringWorRequest)
                .then(compressingWorkRequest!!)
                .then(uploadingWorRequest!!)
                .enqueue()
    }

    private fun setPeriodWorkRequest() { Log.d(TAG, "setPeriodWorkRequest()")
        val periodicWorkRequest : PeriodicWorkRequest = PeriodicWorkRequest
                .Builder(DownloadingWorker::class.java, 15, TimeUnit.MINUTES)
                .build()
        workManager.enqueue(periodicWorkRequest)
    }

    public fun observeOneTimeWorkRequest() : LiveData<WorkInfo> { Log.d(TAG,"observeOneTimeWorkRequest()")
        return if (oneTimeWorkRequest != null) workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest!!.getId())
        else MutableLiveData<WorkInfo>()
    }

    public fun observeFilteringWorRequest() : LiveData<WorkInfo> { Log.d(TAG,"observeFilteringWorRequest()")
        return if (filteringWorRequest != null) workManager.getWorkInfoByIdLiveData(filteringWorRequest!!.getId())
        else MutableLiveData<WorkInfo>()
    }

    public fun observeCompressingWorRequest() : LiveData<WorkInfo> { Log.d(TAG,"observeCompressingWorRequest()")
        return if (compressingWorkRequest != null) workManager.getWorkInfoByIdLiveData(compressingWorkRequest!!.getId())
        else MutableLiveData<WorkInfo>()
    }

    public fun observeUploadingWorRequest() : LiveData<WorkInfo> { Log.d(TAG,"observeUploadingWorRequest()")
        return if (uploadingWorRequest != null) workManager.getWorkInfoByIdLiveData(uploadingWorRequest!!.getId())
        else MutableLiveData<WorkInfo>()
    }

    public fun observeDownloadingWorRequest() : LiveData<WorkInfo> { Log.d(TAG,"observeDownloadingWorRequest()")
        return if (downloadingWorRequest != null) workManager.getWorkInfoByIdLiveData(downloadingWorRequest!!.getId())
        else MutableLiveData<WorkInfo>()
    }

    public fun observeWorkInfosByTagLiveData(name : String) : LiveData<List<WorkInfo>> {
        Log.d(TAG,"observeWorkInfosByTagLiveData($name)")
        return workManager.getWorkInfosByTagLiveData(name)
    }
}