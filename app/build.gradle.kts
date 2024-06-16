plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
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

        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
        buildConfigField("String", "MAPS_API_KEY", "\"AIzaSyBL1ivhwWccabvJ1yxx2KD_dY3EFH5l9CM\"")
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
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    val lottieVersion = "3.4.1"
    val cameraXVersion = "1.3.3"
    val verLifecycle = "2.8.1"
    val verRoom = "2.6.1"

    // Core AndroidX libraries
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
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

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Lifecycle and UI ViewModel support
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$verLifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$verLifecycle")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$verLifecycle")
    implementation("androidx.activity:activity-ktx:1.9.0")

    // Lottie Loading
    implementation ("com.airbnb.android:lottie:$lottieVersion")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("org.robolectric:robolectric:4.5.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0") // InstantTaskExecutorRule
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") //TestDispatcher
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito:mockito-inline:4.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Pagging & Room Runtime
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")
    implementation("androidx.room:room-paging:$verRoom")
    implementation ("androidx.room:room-ktx:$verRoom")
    ksp("androidx.room:room-compiler:$verRoom")
    implementation ("androidx.room:room-runtime:$verRoom")

    // Data storage
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // JSON serialization/deserialization
    implementation("com.google.code.gson:gson:2.10.1")
}
