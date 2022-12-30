package uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private const val PREFERENCES = "Storage"

class Storage(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES)
    }

     suspend fun getString(key: String): String? {
        val sharedPrefKey = stringPreferencesKey(key)
        return context.dataStore.data.first()[sharedPrefKey]
     }

    suspend fun saveString(value: String, key: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }
    suspend fun saveInt(value: Int, key: String) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }
    suspend fun getInt(key: String): Int? {
        val sharedPrefKey = intPreferencesKey(key)
        return context.dataStore.data.first()[sharedPrefKey]
    }

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    suspend fun setBoolean(value: Boolean, key: String) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean? {
        val sharedPrefKey = booleanPreferencesKey(key)
        return context.dataStore.data.first()[sharedPrefKey]
    }
}


