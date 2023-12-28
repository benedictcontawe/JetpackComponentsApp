package com.example.jetpackcomponentsapp.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.jetpackcomponentsapp.util.Constants

public object PreferenceKeys {
    val BOOLEAN_KEY : Preferences.Key<Boolean> = booleanPreferencesKey(Constants.BOOLEAN_KEY)
    val STRING_KEY : Preferences.Key<String> = stringPreferencesKey(Constants.STRING_KEY)
    val INTEGER_KEY : Preferences.Key<Int> = intPreferencesKey(Constants.INTEGER_KEY)
    val DOUBLE_KEY : Preferences.Key<Double> = doublePreferencesKey(Constants.DOUBLE_KEY)
    val LONG_KEY : Preferences.Key<Long> = longPreferencesKey(Constants.LONG_KEY)
    //val LIST_MODEL_KEY : Preferences.Key<List<CustomModel>> = preferencesKey<List<CustomModel>>("list_model_key")
}