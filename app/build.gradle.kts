plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.whiskerbuds"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.whiskerbuds"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/io.netty.versions.properties"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //  implementation(libs.androidx.navigation.runtime.android)
  //  implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.appdistribution.gradle)
    implementation(libs.firebase.auth)
   // implementation(libs.navigation.runtime.android)
    implementation(libs.androidx.ui.text.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.circleimageview)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)  // Replace with the latest version
    implementation (libs.androidx.lifecycle.viewmodel.ktx.v231)
    implementation(libs.androidx.room.ktx)  // Replace with the latest version
    implementation(libs.retrofit2.kotlin.coroutines.adapter)  // Replace with the latest version

    implementation(libs.picasso)

    implementation(libs.play.services.wallet)

    //Cloudinary
    implementation(libs.cloudinary.android)
    implementation(libs.cloudinary.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Coil
    implementation(libs.coil.compose)

    val nav_version = "2.8.8"

    // Jetpack Compose Navigation
    implementation(libs.androidx.navigation.compose.v288)

    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    // JSON Serialization (Only if using Kotlin serialization for navigation arguments)
    implementation(libs.kotlinx.serialization.json)

    implementation("org.tensorflow:tensorflow-lite:2.13.0")


//Glide
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

}