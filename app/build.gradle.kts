plugins {
    id("com.android.application")
    // AGP 9 compiles Kotlin itself (built-in Kotlin), so org.jetbrains.kotlin.android is no longer applied here.
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.jetpackcompose"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.jetpackcompose"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    // Kotlin's jvmTarget now follows compileOptions.targetCompatibility (the old kotlinOptions block was removed in AGP 9).
    buildFeatures {
        compose = true
    }
    // composeOptions.kotlinCompilerExtensionVersion is gone: the Compose compiler comes from the
    // org.jetbrains.kotlin.plugin.compose plugin and always matches the Kotlin version.
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //region Android X Library
    implementation("androidx.core:core-ktx:1.19.1")
    implementation("androidx.core:core-splashscreen:1.2.0")
    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    //implementation("androidx.appcompat:appcompat:1.6.1")
    //implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //endregion
    //region Android X Jetpack Compose
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.material:material:1.12.1")
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata:1.12.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
    //endregion
    //region Google Library
    //implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")
    implementation("com.google.android.material:material:1.14.0")
    implementation("com.google.code.gson:gson:2.15.0")
    //endregion
    //region Squareup Library
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:5.5.1"))
    implementation("com.squareup.okhttp3:okhttp:5.5.1")
    implementation("com.squareup.okhttp3:logging-interceptor:5.5.1")
    //endregion
    //region Glide
    //implementation("com.github.bumptech.glide:glide:4.15.1")
    //implementation("com.github.bumptech.glide:annotations:4.15.1")
    //annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta10")
    //endregion
    //region Android Unit Test and U.I. Test Library
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //endregion
}