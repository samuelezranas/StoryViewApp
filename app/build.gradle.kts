plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.dicoding.storyviewapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.storyviewapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "6.9"

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
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
        }
    }
}

dependencies {

    val lottieVersion = "3.4.0"
    val cameraXVersion = "1.3.3"
    // Core AndroidX libraries
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Image loading and manipulation
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // CameraX
    implementation ("androidx.camera:camera-camera2:$cameraXVersion")
    implementation ("androidx.camera:camera-lifecycle:$cameraXVersion")
    implementation ("androidx.camera:camera-view:$cameraXVersion")

    // Network communication
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Lifecycle and UI ViewModel support
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")

    // Glass-morphism Background
    implementation ("jp.wasabeef:blurry:4.0.0")
    implementation("androidx.camera:camera-lifecycle:1.3.3")

    // Lottie Loading
    implementation ("com.airbnb.android:lottie:$lottieVersion")

    // Glassmorphism Background
    implementation ("jp.wasabeef:blurry:4.0.0")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Data storage
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // JSON serialization/deserialization
    implementation("com.google.code.gson:gson:2.10.1")
}
