import java.io.FileInputStream
import java.util.Properties
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

val localPropertiesFile = rootProject.file("./local.properties")
val property = Properties()
if (localPropertiesFile.exists()) {
    property.load(FileInputStream(localPropertiesFile))
}
val accessKey = property["WEATHER_API_KEY"] as String

android {
    namespace = "com.weatherly.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        buildConfigField("String", "API_KEY", accessKey)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    ktlint {
        android.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    api(libs.kotlin.stdlib.jdk8)
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    // Retrofit
    api(libs.retrofit)
    api(libs.okhttp.logging.interceptor)
    api(libs.kotlinx.serialization.json)
    api(libs.retrofit.kotlinx.serialization.converter)

    // Utility libraries
    api(libs.coil)

    api(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
