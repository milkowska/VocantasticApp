package uk.ac.aber.dcs.cs31620.vocantastic.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.ac.aber.dcs.cs31620.vocantastic.storage.Storage
import javax.inject.Inject

/**
 *  Responsible for preparing and managing the data for activity. It is a part of dataStore preferences.
 */
@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {

    fun saveBoolean(value: Boolean, key: String) {
        viewModelScope.launch {
            storage.setBoolean(value, key)
        }
    }

    fun saveInt(value: Int, key: String) {
        viewModelScope.launch {
            storage.saveInt(value, key)
        }
    }

    fun getInt(key: String): Int? = runBlocking {
        storage.getInt(key)
    }

    fun getBoolean(key: String): Boolean? = runBlocking {
        storage.getBoolean(key)
    }

    fun getString(key: String): String? = runBlocking {
        storage.getString(key)
    }

    fun saveString(value: String, key: String) {
        viewModelScope.launch {
            storage.saveString(value, key)
        }
    }
}