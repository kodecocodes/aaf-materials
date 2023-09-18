package com.kodeco.recipefinder.data

interface StoredPreferences {
  suspend fun saveString(key: String, value: String)

  suspend fun getString(key: String): String?

  suspend fun saveInt(key: String, value: Int)

  suspend fun getInt(key: String): Int?

  suspend fun hasKey(key: String): Boolean
}