package com.example.jetpackcomponentsapp.repository

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.jetpackcomponentsapp.model.CustomModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import java.io.IOException

class CustomRepository(applicationContext: Application) : BaseRepository {


    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null
        private var dataStore : DataStore<Preferences>? = null
        fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    init {
        dataStore = applicationContext.getBaseContext().createDataStore(name = "data_store")
    }
    //region CRUD Operation
    override suspend fun update(booleanKey : Boolean) {
        dataStore?.edit { preference ->
            preference.set(PreferenceKeys.BOOLEAN_KEY, booleanKey)
        }
    }

    override suspend fun update(stringKey : String) {
        dataStore?.edit { preference ->
            preference.set(PreferenceKeys.STRING_KEY, stringKey)
        }
    }

    override suspend fun update(integerKey : Int) {
        dataStore?.edit { preference ->
            preference.set(PreferenceKeys.INTEGER_KEY, integerKey)
        }
    }

    override suspend fun update(doubleKey : Double) {
        dataStore?.edit { preference ->
            preference.set(PreferenceKeys.DOUBLE_KEY, doubleKey)
        }
    }

    override suspend fun update(longKey : Long) {
        dataStore?.edit { preference ->
            preference.set(PreferenceKeys.LONG_KEY, longKey)
        }
    }

    fun getBoolean() : Flow<Boolean> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.BOOLEAN_KEY) ?: false
        } ?: emptyFlow()
    }

    fun getString() : Flow<String> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.STRING_KEY) ?: "Nil"
        } ?: emptyFlow()
    }

    fun getInteger() : Flow<Int> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.INTEGER_KEY) ?: 0
        } ?: emptyFlow()
    }

    fun getDouble() : Flow<Double> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.DOUBLE_KEY) ?: 0.00
        } ?: emptyFlow()
    }

    fun getLong() : Flow<Long> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.LONG_KEY) ?: 0L
        } ?: emptyFlow()
    }

    fun getCustomModel() : Flow<List<CustomModel>> {
        return dataStore?.data?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }?.map { preference ->
            preference.get(PreferenceKeys.LIST_MODEL_KEY) ?: emptyList<CustomModel>()
        } ?: emptyFlow()
    }
    //endregion
}