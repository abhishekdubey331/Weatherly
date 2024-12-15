import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.weatherly.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weatherly.app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Compose UI
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.material)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.splashscreen)

    // AndroidX Core
    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.runtime.compose)

    // Coil for Image Loading
    implementation(libs.coil.compose)

    // Dagger - Hilt (for Dependency Injection)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Testing
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    androidTestImplementation(libs.mockito.android)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.junit.ext)
}
