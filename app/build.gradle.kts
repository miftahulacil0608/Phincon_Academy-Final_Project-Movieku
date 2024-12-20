import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.example.movieku"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieku"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val secretClient = properties.getProperty("WEB_SECRET_CLIENT")

        buildConfigField(
            type = "String",
            name = "WEB_SECRET_CLIENT",
            value = secretClient
        )


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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



}

dependencies {
    implementation(project(":domain"))
    implementation(project(":di"))

    //hilt
    implementation(libs.hilt.dagger)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.googleid)
    implementation(libs.firebase.crashlytics)
    kapt(libs.hilt.compiler)

    //paging
    implementation(libs.androidx.paging)

    //Jetpack nav
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    //lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //view pager 2
    implementation(libs.androidx.viewpager2)

    //swipe refresh layout
    implementation(libs.androidx.swiperefreshlayout)

    //Glide
    implementation(libs.glide)

    //shimmer
    implementation(libs.shimmer)

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    //single row calendar
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation(files("../single-row-calendar-release.aar"))



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
