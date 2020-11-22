package com.example.jetpackcomponentsapp.repository

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import com.example.jetpackcomponentsapp.model.CustomModel

object PreferenceKeys {
    val BOOLEAN_KEY : Preferences.Key<Boolean> = preferencesKey<Boolean>("boolean_key")
    val STRING_KEY : Preferences.Key<String> = preferencesKey<String>("string_key")
    val INTEGER_KEY : Preferences.Key<Int> = preferencesKey<Int>("integer_key")
    val DOUBLE_KEY : Preferences.Key<Double> = preferencesKey<Double>("double_key")
    val LONG_KEY : Preferences.Key<Long> = preferencesKey<Long>("long_key")
    val LIST_MODEL_KEY : Preferences.Key<List<CustomModel>> = preferencesKey<List<CustomModel>>("list_model_key")
}