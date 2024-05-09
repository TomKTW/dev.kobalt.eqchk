plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.kobalt.eqchk.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "dev.kobalt.eqchk.android"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {
    // Android JDK Desugaring
    coreLibraryDesugaring(libs.android.desugar)
    // AndroidX Activity Compose Support
    implementation(libs.androidx.compose.activity)
    // AndroidX Compose Foundation Components
    implementation(libs.androidx.compose.foundation)
    // AndroidX Compose Foundation Layout Components
    implementation(libs.androidx.compose.foundation.layout)
    // AndroidX Compose Material Design Components
    implementation(libs.androidx.compose.material)
    // AndroidX Compose Material Design 3 Components
    implementation(libs.androidx.compose.material3)
    // AndroidX Compose Runtime
    implementation(libs.androidx.compose.runtime)
    // AndroidX Compose UI
    implementation(libs.androidx.compose.ui)
    // AndroidX Compose UI Tools
    implementation(libs.androidx.compose.ui.tools)
    // AndroidX Core
    implementation(libs.androidx.core)
    // AndroidX Fragment
    implementation(libs.androidx.fragment)
    // AndroidX Lifecycle Java 8 Common
    implementation(libs.androidx.lifecycle.common)
    // AndroidX Lifecycle Extensions
    implementation(libs.androidx.lifecycle.extensions)
    // AndroidX Lifecycle Runtime
    implementation(libs.androidx.lifecycle.runtime)
    // AndroidX Lifecycle Runtime Compose Support
    implementation(libs.androidx.lifecycle.runtime.compose)
    // AndroidX Lifecycle View Model Support
    implementation(libs.androidx.lifecycle.viewmodel)
    // AndroidX Navigation Compose Support
    implementation(libs.androidx.navigation.compose)
    // AndroidX Navigation Fragment Stack
    implementation(libs.androidx.navigation.fragment)
    // AndroidX Navigation UI Stack
    implementation(libs.androidx.navigation.ui)
    // AndroidX Preferences
    implementation(libs.androidx.preference)
    // AndroidX Room Database Framework
    implementation(libs.androidx.room)
    // AndroidX Room Database Annotation Processor
    ksp(libs.androidx.room.compiler)
    // AndroidX Work Manager
    implementation(libs.androidx.work.runtime)
    // Hilt Dependency Injection Framework
    implementation(libs.hilt.android)
    // Hilt Dependency Injection Annotation Processor
    ksp(libs.hilt.android.compiler)
    // Hilt Dependency Injection Compose Navigation Support
    implementation(libs.hilt.androidx.navigation.compose)
    // Hilt Dependency Injection Worker Support
    implementation(libs.hilt.androidx.work)
    // Hilt Dependency Injection AndroidX Annotation Processor
    ksp(libs.hilt.androidx.compiler)
    // KotlinX Coroutines Core
    implementation(libs.kotlinx.coroutines.core)
    // KotlinX Coroutines Android
    implementation(libs.kotlinx.coroutines.android)
    // KotlinX Serialization Json
    implementation(libs.kotlinx.serialization.json)
    // Ktor HTTP Android Client
    implementation(libs.ktor.client.android)
    // Ktor HTTP Logging Support
    implementation(libs.ktor.client.logging)
    // OpenStreetMaps Map View
    implementation(libs.osm)
    // OpenStreetMaps Extras
    implementation(libs.osm.extras)
    // Simple Logging Facade
    implementation(libs.slf4j)
    // JUnit
    testImplementation(libs.junit)
    // AndroidX JUnit
    androidTestImplementation(libs.junit.androidx)
    // AndroidX Espresso Core
    androidTestImplementation(libs.espresso.androidx)
}