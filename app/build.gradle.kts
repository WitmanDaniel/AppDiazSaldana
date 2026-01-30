plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "unc.edu.pe.appdiazsaldana"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "unc.edu.pe.appdiazsaldana"
        minSdk = 28
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
    // Para usar RecyclerView (la lista de reservas)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// Para usar CardView (el diseño de cada item en la lista)
    implementation("androidx.cardview:cardview:1.0.0")
}