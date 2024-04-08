plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false


}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}