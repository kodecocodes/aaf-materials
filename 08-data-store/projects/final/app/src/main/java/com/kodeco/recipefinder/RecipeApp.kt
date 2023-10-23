package com.kodeco.recipefinder

import android.app.Application
import com.kodeco.recipefinder.data.Prefs
import timber.log.Timber
import timber.log.Timber.Forest.plant


class RecipeApp : Application() {
  lateinit var prefs: Prefs

  override fun onCreate() {
    super.onCreate()
    // Install a Timber tree.
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }

    prefs = Prefs(this)
  }
}
