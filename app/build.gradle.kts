import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

apply(from = "../shared_dependencies.gradle")

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))
val BASE_URL_IMAGE    = localProperties["BASE_URL_IMAGE"] ?: "Define your Base Url Image"
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
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        forEach { type ->
            type.buildConfigField("long", "TIMEOUT", TIMEOUT.toString())
            type.buildConfigField("String", "BASE_URL", BASE_URL.toString())
            type.buildConfigField("String", "BASE_URL_IMAGE", BASE_URL_IMAGE.toString())
            type.buildConfigField("String", "API_KEY", API_KEY.toString())
            type.buildConfigField("String", "TOKEN", TOKEN.toString())
        }
        debug {
            buildConfigField("String", "BASE_URL", "${BASE_URL}")
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
        sourceCompatibility =  JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    dynamicFeatures += setOf(":favorite")
    kapt {
        correctErrorTypes = true
    }
    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.activity)
    api(libs.google.play.base)

    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api("com.google.android.play:feature-delivery-ktx:2.1.0")
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.navigation.dynamicFeatures.fragment)
    api(libs.navigation.dynamicFeatures.runtime)
    implementation(libs.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)

    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.mockito:mockito-inline:4.4.0")

}