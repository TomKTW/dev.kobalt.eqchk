plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
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
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    // AndroidX Core Kotlin Extensions
    implementation("androidx.core:core-ktx:1.7.0")
    // AndroidX Activity Compose Support
    implementation("androidx.activity:activity-compose:1.6.5")
    // AndroidX AppCompat
    implementation("androidx.appcompat:appcompat:1.4.1")
    // AndroidX Compose Foundation Components
    implementation("androidx.compose.foundation:foundation:1.6.5")
    // AndroidX Compose Foundation Layout Components
    implementation("androidx.compose.foundation:foundation-layout:1.6.5")
    // AndroidX Compose Material Design Components
    implementation("androidx.compose.material:material:1.6.5")
    // AndroidX Compose Runtime
    implementation("androidx.compose.runtime:runtime:1.6.5")
    // AndroidX Compose UI
    implementation("androidx.compose.ui:ui:1.6.5")
    // AndroidX Compose UI Tools
    implementation("androidx.compose.ui:ui-tooling:1.6.5")
    // AndroidX Core Kotlin Extensions
    implementation("androidx.core:core-ktx:1.7.0")
    // AndroidX Fragment Kotlin Extensions
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    // AndroidX Lifecycle Extensions
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // AndroidX Lifecycle Extensions
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    // AndroidX Lifecycle Java 8 Common
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.1")
    // AndroidX Lifecycle Runtime Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    // AndroidX Lifecycle LiveData Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    // AndroidX Lifecycle ViewModel Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // AndroidX Navigation Compose Support
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // AndroidX Navigation Fragment Stack
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    // AndroidX Navigation UI Stack
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // AndroidX Preferences Kotlin Extensions
    implementation("androidx.preference:preference-ktx:1.2.0")
    // AndroidX Room Database Framework
    implementation("androidx.room:room-runtime:2.6.1")
    // AndroidX Room Database Kotlin Extensions
    implementation("androidx.room:room-ktx:2.6.1")
    // AndroidX Room Database Annotation Processor
    ksp("androidx.room:room-compiler:2.6.1")
    // AndroidX Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // AndroidX WorkManager Kotlin Extensions
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    // Google Material Design
    implementation("com.google.android.material:material:1.5.0")
    // Hilt Dependency Injection Framework
    implementation("com.google.dagger:hilt-android:2.51.1")
    // Hilt Dependency Injection Annotation Processor
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    // Hilt Dependency Injection Compose Navigation Support
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // Hilt Dependency Injection Worker Support
    implementation("androidx.hilt:hilt-work:1.2.0")
    // Hilt Dependency Injection AndroidX Annotation Processor
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    // KotlinX Coroutines Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    // KotlinX Coroutines Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    // KotlinX Serialization Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    // Ktor HTTP Android Client
    implementation("io.ktor:ktor-client-android:1.6.1")
    // Ktor HTTP Logging
    implementation("io.ktor:ktor-client-logging:1.6.1")
    // OpenStreetMaps Map View
    implementation("org.osmdroid:osmdroid-android:6.1.11")
    // OpenStreetMaps Extras
    implementation("com.github.MKergall:osmbonuspack:6.8.0")
    // Simple Logging Facade
    implementation("org.slf4j:slf4j-android:1.7.32")
    // JUnit
    testImplementation("junit:junit:4.13.2")
    // AndroidX JUnit
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    // AndroidX Espresso Core
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}
