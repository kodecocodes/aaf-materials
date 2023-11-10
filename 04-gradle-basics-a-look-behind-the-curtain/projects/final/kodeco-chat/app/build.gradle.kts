import java.io.FileInputStream
import java.util.Properties

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
  }
  signingConfigs {
    create("release") {
      keyAlias = keysProperties["keyAlias"] as String
      keyPassword = keysProperties["keyPassword"] as String
      storeFile = file(keysProperties["storeFile"] as String)
      storePassword = keysProperties["storePassword"] as String
    }
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
          getDefaultProguardFile("proguard-android-optimize.txt"),
          "proguard-rules.pro"
      )
      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      applicationIdSuffix = ".debug"
    }
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
}

dependencies {
  val composeBom = platform(libs.androidx.compose.bom)

  implementation(composeBom)
  implementation(libs.bundles.androidx)
  implementation(libs.bundles.compose)

  testImplementation(libs.bundles.unit.tests)
  androidTestImplementation(composeBom)
  androidTestImplementation(libs.bundles.instrumented.tests)

  debugImplementation(libs.bundles.compose.debug)
}
