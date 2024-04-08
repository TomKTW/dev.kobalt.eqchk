plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
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
        viewBinding = true
    }
}

dependencies {
    // Android JDK Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    // AndroidX Core Kotlin Extensions
    implementation("androidx.core:core-ktx:1.7.0")
    // AndroidX AppCompat
    implementation("androidx.appcompat:appcompat:1.4.1")
    // AndroidX Core Kotlin Extensions
    implementation("androidx.core:core-ktx:1.7.0")
    // AndroidX Fragment Kotlin Extensions
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    // AndroidX Lifecycle Extensions
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    // AndroidX Lifecycle Java 8 Common
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.1")
    // AndroidX Lifecycle Runtime Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    // AndroidX Lifecycle LiveData Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    // AndroidX Lifecycle ViewModel Kotlin Extensions
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    // AndroidX Navigation Fragment Stack
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    // AndroidX Navigation UI Stack
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // AndroidX Preferences Kotlin Extensions
    implementation("androidx.preference:preference-ktx:1.2.0")
    // AndroidX Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // AndroidX WorkManager Kotlin Extensions
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    // Exposed Core
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    // Exposed DAO
    implementation("org.jetbrains.exposed:exposed-dao:0.37.3")
    // Exposed JDBC
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
    // Exposed Java Time Extension
    implementation("org.jetbrains.exposed:exposed-java-time:0.37.3")
    // Google Material Design
    implementation("com.google.android.material:material:1.5.0")
    // H2 Database
    implementation("com.h2database:h2:1.4.200")
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