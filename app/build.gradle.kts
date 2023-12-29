plugins {
    id("com.android.application")
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
        vectorDrawables.useSupportLibrary = true
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    //region Android X Library
    //implementation("androidx.core:core-ktx:1.12.0")
    //implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")//For control over item selection of both touch and mouse driven selection
    //implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
    //implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    //implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    //endregion
    //region Android Unit Test and U.I. Test Library
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //endregion
    //region jetbrains Library
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")
    //endregion
}