package com.example.jetpackcomponentsapp.repository

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.edit
import com.example.jetpackcomponentsapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class CustomRepository : BaseRepository {

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null
        public fun getInstance(applicationContext : Application) : CustomRepository {
            return INSTANCE ?: CustomRepository(applicationContext)
        }
    }

    constructor(applicationContext : Application) {
        this.applicationContext = applicationContext
    }

    private val Context.dataStore by preferencesDataStore (
        name = Constants.DATA_STORE
    )

    private val applicationContext : Application

    init {

    }
    //region CRUD Operation
    override suspend fun update(booleanKey : Boolean) {
        applicationContext.dataStore.edit { preference ->
            preference[PreferenceKeys.BOOLEAN_KEY] = booleanKey
        }
    }

    override suspend fun update(stringKey : String) {
        applicationContext.dataStore.edit { preference ->
            preference[PreferenceKeys.STRING_KEY] = stringKey
        }
    }

    override suspend fun update(integerKey : Int) {
        applicationContext.dataStore.edit { preference ->
            preference[PreferenceKeys.INTEGER_KEY] = integerKey
        }
    }

    override suspend fun update(doubleKey : Double) {
        applicationContext.dataStore.edit { preference ->
            preference[PreferenceKeys.DOUBLE_KEY] = doubleKey
        }
    }

    override suspend fun update(longKey : Long) {
        applicationContext.dataStore.edit { preference ->
            preference[PreferenceKeys.LONG_KEY] = longKey
        }
    }

    public fun getBoolean() : Flow<Boolean?> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.BOOLEAN_KEY] ?: null
        }
    }

    public fun getString() : Flow<String> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.STRING_KEY] ?: "Nil"
        }
    }

    public fun getInteger() : Flow<Int?> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.INTEGER_KEY] ?: null
        }
    }

    public fun getDouble() : Flow<Double?> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.DOUBLE_KEY] ?: null
        }
    }

    public fun getLong() : Flow<Long?> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.LONG_KEY] ?: null
        }
    }
    /*
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
    */
    //endregion
}