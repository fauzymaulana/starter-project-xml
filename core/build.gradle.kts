import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
//    alias(libs.plugins.navigation.safeargs)
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("kotlin-android")
}

apply(from = "../shared_dependencies.gradle")

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))
val TIMEOUT     = localProperties["TIMEOUT"] ?: 0L
val BASE_URL    = localProperties["BASE_URL"] ?: "Define your Base Url"
val API_KEY     = localProperties["API_KEY"] ?: "Define your Api Key"
val TOKEN       = localProperties["TOKEN"] ?: "Define your Token"

android {
    namespace = "com.papero.capstoneexpert.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    /** Views */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    /** Room DB */
    api("androidx.room:room-ktx:2.2.5")
    api("androidx.room:room-runtime:2.2.5")
    api("androidx.room:room-rxjava2:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    androidTestImplementation("androidx.room:room-testing:2.2.5")

    /** Test */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.androidx.annotation)
}