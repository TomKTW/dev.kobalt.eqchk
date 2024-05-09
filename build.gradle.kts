plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safeArgsGradlePlugin)
    }
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}