plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.db_demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.db_demo"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room dependencies
    // Read doc here: https://developer.android.com/training/data-storage/room
    // Room

    // Room components
    implementation("androidx.room:room-runtime:2.8.2")
    annotationProcessor("androidx.room:room-compiler:2.8.2")

    // Lifecycle components
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.9.4")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata:2.9.4")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime:2.9.4")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.4")

    // Annotation processor
    implementation("androidx.lifecycle:lifecycle-common-java8:2.9.4")

}