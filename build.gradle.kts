// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        google()
    }
    dependencies {
        classpath(libs.android.gradlePlugin)
//        classpath("io.realm:realm-gradle-plugin:10.15.1")
//        classpath("io.realm.kotlin:gradle-plugin:1.10.2"
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath(libs.navigation.safeArgs.gradlePlugin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}