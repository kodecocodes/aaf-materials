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
    vectorDrawables {
      useSupportLibrary = true
    }
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
      isShrinkResources = true
      isMinifyEnabled = true

      proguardFiles(
          getDefaultProguardFile("proguard-android-optimize.txt"),
          "proguard-rules.pro"
      )

      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      applicationIdSuffix = ".debug"

      isDebuggable = true
      isShrinkResources = false
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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

  testImplementation(libs.bundles.unit.tests)
  androidTestImplementation(composeBom)
  androidTestImplementation(libs.bundles.instrumented.tests)

  debugImplementation(libs.bundles.compose.debug)
}
