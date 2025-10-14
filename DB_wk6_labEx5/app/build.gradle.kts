plugins {
    alias(libs.plugins.android.application)  // Keep your Android plugin
    kotlin("android")                        // Kotlin plugin
    kotlin("kapt")                            // KAPT plugin
}

android {
    namespace = "com.example.db_wk6_labex5"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.db_wk6_labex5"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"  // Must match your Java target
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // implementation(libs.room.common.jvm)
    // implementation(libs.room.runtime.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // implementation("androidx.room:room-runtime:2.8.2")
    // kapt("androidx.room:room-compiler:2.8.2")

    // Optional: for Kotlin use kapt instead of annotationProcessor
    // kapt "androidx.room:room-compiler:2.6.1"
}

// Add this **after dependencies block**
tasks.withType<Test> {
    enabled = false
}