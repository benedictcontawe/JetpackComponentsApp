plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.jetpackcomponentsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetpackcomponentsapp"
        minSdk = 16
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    //region Android UI Layout Library and backward-compatible Library(Legacy)
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.android.material:material:1.11.0")
    //endregion
    //region Android Unit Test and U.I. Test Library
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //endregion
    //region Room -> use annotationProcessor for java, kapt for kotlin
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1") //android.arch.persistence.room:runtime
    kapt("androidx.room:room-compiler:2.6.1") //android.arch.persistence.room:compiler
    testImplementation("androidx.room:room-testing:2.6.1") //android.arch.persistence.room:testing
    //endregion
    //region Work Manager
    //implementation("androidx.work:work-runtime:2.9.0")// (Java only)
    //implementation("androidx.work:work-runtime-ktx:2.9.0")// Kotlin + coroutines
    //endregion
}