package com.kodeco.recipefinder.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecurePrefs(val context: Context): StoredPreferences {
  private val prefs: SharedPreferences

  init {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    prefs = EncryptedSharedPreferences.create(
      "encrypted_preferences", // fileName
      masterKeyAlias, // masterKeyAlias
      context, // context
      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // prefKeyEncryptionScheme
      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // prefvalueEncryptionScheme
    )
  }

  override suspend fun saveString(key: String, value: String) {
    prefs.edit().putString(key, value).apply()
  }

  override suspend fun getString(key: String): String? {
    return prefs.getString(key, null)
  }

  override suspend fun saveInt(key: String, value: Int) {
    prefs.edit().putInt(key, value).apply()
  }

  override suspend fun getInt(key: String): Int? {
    return prefs.getInt(key, 0)
  }

  override suspend fun hasKey(key: String): Boolean {
    return prefs.contains(key)
  }
}
