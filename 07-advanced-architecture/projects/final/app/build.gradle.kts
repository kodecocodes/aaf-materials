import java.io.FileInputStream
import java.util.Properties

/*
 * Copyright (c) 2023 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

val keysPropertiesFile: File = rootProject.file("keys.properties")
val keysProperties = Properties()
keysProperties.load(FileInputStream(keysPropertiesFile))

android {
  namespace = "com.kodeco.chat"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.kodeco.chat"
    minSdk = 30
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      buildConfigField("String", "DITTO_APP_ID", keysProperties["DITTO_APP_ID"] as String)
      buildConfigField("String", "DITTO_TOKEN", keysProperties["DITTO_TOKEN"] as String)
    }
    debug {
      buildConfigField("String", "DITTO_APP_ID", keysProperties["DITTO_APP_ID"] as String)
      buildConfigField("String", "DITTO_TOKEN", keysProperties["DITTO_TOKEN"] as String)
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.3"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  val composeBom = platform(libs.androidx.compose.bom)

  implementation(composeBom)
  implementation(libs.bundles.androidx)
  implementation(libs.bundles.compose)

  // compose lifecycle
  implementation(libs.androidx.lifecycle.runtime.compose)

  // used by theme
  implementation(libs.androidx.appcompat)
  // additional material icons
  implementation(libs.androidx.material.icons.extended)

  // SplashScreen compat library
  implementation(libs.androidx.core.splashscreen)

  // downloadable fonts
  implementation(libs.androidx.ui.text.google.fonts)
  //for rememberImagePainter and image loading functionality
  implementation(libs.coil.compose)

  // Date Time Library - the latest way to handle dates in Kotlin
  implementation(libs.kotlinx.datetime)

  // Ditto SDK
  implementation(libs.ditto)

  testImplementation(libs.bundles.unit.tests)
  androidTestImplementation(composeBom)
  androidTestImplementation(libs.bundles.instrumented.tests)

  debugImplementation(libs.bundles.compose.debug)
}
