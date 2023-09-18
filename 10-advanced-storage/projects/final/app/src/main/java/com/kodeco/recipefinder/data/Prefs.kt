package com.kodeco.recipefinder.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class Prefs(val context: Context): StoredPreferences {
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recipes")

  override suspend fun saveString(key: String, value: String) {
    val prefKey = stringPreferencesKey(key)
    context.dataStore.edit { prefs ->
      prefs[prefKey] = value
    }
  }

  override suspend fun getString(key: String): String? {
    val prefKey = stringPreferencesKey(key)
    return context.dataStore.data.first()[prefKey]
  }

  override suspend fun saveInt(key: String, value: Int) {
    val prefKey = intPreferencesKey(key)
    context.dataStore.edit { prefs ->
      prefs[prefKey] = value
    }
  }

  override suspend fun getInt(key: String): Int? {
    val prefKey = intPreferencesKey(key)
    return context.dataStore.data.first()[prefKey]
  }

  override suspend fun hasKey(key: String): Boolean {
    val prefKey = stringPreferencesKey(key)
    return context.dataStore.data.first().contains(prefKey)
  }
}
