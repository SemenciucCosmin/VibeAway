import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import java.util.Properties

val localProperties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

val amadeusApiKey = localProperties.getProperty("AMADEUS_API_KEY") ?: ""
val amadeusApiSecret = localProperties.getProperty("AMADEUS_API_SECRET") ?: ""

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt.gradle)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.vibeaway"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vibeaway"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "AMADEUS_API_KEY", "\"${amadeusApiKey}\"")
        buildConfigField("String", "AMADEUS_API_SECRET", "\"${amadeusApiSecret}\"")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // ANDROID X
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.startup.runtime)

    // COIL
    implementation(platform(libs.coil.bom))
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // COMPOSE
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // DETEKT
    detektPlugins(libs.detekt.formatting)
    implementation(libs.okhttp3)

    // FIREBASE
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    // GOOGLE
    implementation(libs.play.services.auth)
    implementation(libs.google.id)

    // KOIN
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.compose)

    // KOTLIN X
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.converter)

    // NAVIGATION
    implementation(libs.navigation.compose)
    implementation(libs.navigation.runtime.ktx)

    // NETWORK
    implementation(platform(libs.retrofit.bom))
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

}

detekt {
    source.setFrom(
        DEFAULT_SRC_DIR_JAVA,
        DEFAULT_SRC_DIR_KOTLIN,
        "${project.rootDir}/app/$DEFAULT_SRC_DIR_JAVA",
        "${project.rootDir}/app/$DEFAULT_SRC_DIR_KOTLIN",
    )
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    config.setFrom("$rootDir/detekt/detekt.yml")
}
