plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.zybooks.practicepals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zybooks.practicepals"
        minSdk = 33
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Room dependencies
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Optional: RxJava2 support for Room
    implementation(libs.room.rxjava2)

    // RecyclerView
    implementation(libs.recyclerview)  // Use this format for Kotlin DSL

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}