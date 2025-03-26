// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    // Use the standard Gradle plugin for Android apps (with explicit version).
    id("com.android.application") version "8.0.2" apply false

    // Use the standard Kotlin plugin (with explicit version).
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    // Use the Hilt plugin (with explicit version).
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
