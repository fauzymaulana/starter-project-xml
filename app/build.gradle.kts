import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
//    alias(libs.plugins.navigation.safeargs)
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

apply(from = "../shared_dependencies.gradle")

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))
val TIMEOUT     = localProperties["TIMEOUT"] ?: 0L
val BASE_URL    = localProperties["BASE_URL"] ?: "Define your Base Url"
val API_KEY     = localProperties["API_KEY"] ?: "Define your Api Key"
val TOKEN       = localProperties["TOKEN"] ?: "Define your Token"

android {
    namespace = "com.papero.capstoneexpert"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.papero.capstoneexpert"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        forEach { type ->
            type.buildConfigField("long", "TIMEOUT", TIMEOUT.toString())
            type.buildConfigField("String", "BASE_URL", BASE_URL.toString())
            type.buildConfigField("String", "API_KEY", API_KEY.toString())
            type.buildConfigField("String", "TOKEN", TOKEN.toString())
        }
        debug {
            buildConfigField("String", "BASE_URL", "${BASE_URL}")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            buildConfigField("String", "BASE_URL", "${BASE_URL}")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.activity)
//    implementation(libs.legacy.support.v4)
//    implementation(libs.androidx.lifecycle.livedata.ktx)
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.rxjava.ktx)
}