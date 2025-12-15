plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.hilt)


    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.thirdeye"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.thirdeye"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding=true
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
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")
    implementation("androidx.camera:camera-extensions:1.3.4")
    implementation("com.google.guava:guava:33.5.0-android")

    implementation("androidx.lifecycle:lifecycle-service:2.8.7")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.9.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")


    kapt("com.github.bumptech.glide:compiler:4.16.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")


    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

}