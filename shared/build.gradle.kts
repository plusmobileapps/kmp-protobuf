import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            api(projects.protobuf)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.engine.java)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.engine.darwin)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.engine.android)
        }
    }
}

android {
    namespace = "com.plusmobileapps.protobuf.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
