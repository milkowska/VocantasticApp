package uk.ac.aber.dcs.cs31620.vocantastic.preferencesStorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private const val PREFERENCES = "Storage"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES)

class Storage (
    private val context: Context
) {

    suspend fun getString(key: String): String? {
        val key = stringPreferencesKey(key)
        return context.dataStore.data.first()[key]
    }

    suspend fun saveString(name: String, key: String) {
        val key = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[key] = name
        }
    }



}