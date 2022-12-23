package uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private const val PREFERENCES = "Storage"

class Storage(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES)
        val USERKEY = stringPreferencesKey("user")
    }

    fun getString(key: String): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: ""
            }
    }

    suspend fun saveString(value: String, key: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun setBoolean(value: Boolean, key: String) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    // or using flow?
    suspend fun clearTable() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun getBoolean(key: String): Boolean? {
        val sharedPrefKey = booleanPreferencesKey(key)
        return context.dataStore.data.first()[sharedPrefKey]
    }
}


