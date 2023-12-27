package com.kodeco.recipefinder

import android.app.Application
import timber.log.Timber
import timber.log.Timber.Forest.plant


class RecipeApp : Application() {
  // TODO: Add Prefs

  override fun onCreate() {
    super.onCreate()
    // Install a Timber tree.
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }

    // TODO: Add Prefs
  }
}
