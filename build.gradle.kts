plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}